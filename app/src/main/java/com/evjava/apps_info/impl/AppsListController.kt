package com.evjava.apps_info.impl

import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.observable.subscribe
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import com.badoo.reaktive.subject.publish.PublishSubject
import com.evjava.apps_info.api.ApperContextI
import com.evjava.apps_info.api.BaseScreenContext
import com.evjava.apps_info.api.Message
import com.evjava.apps_info.api.ScreenControllerI
import com.evjava.apps_info.api.SearchControllerI
import com.evjava.apps_info.api.SearchState
import com.evjava.apps_info.api.data.ItemsState
import com.evjava.apps_info.ui.navigation.Screen
import com.evjava.apps_info.utils.RxUtils.wrapObservable
import io.github.aakira.napier.Napier
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AppsListController(val bsc: BaseScreenContext, screen: Screen.AppsList) : ScreenControllerI, SearchControllerI, ApperContextI by bsc {
    override val title: Observable<String> = "Apps".wrapObservable
    val items = BehaviorSubject(ItemsState.EMPTY)
    override val search = BehaviorSubject(timestampToSearchState(screen.timestamp))
    override val news = PublishSubject<Message>()

    private val allApps = appsProvider.getApps()

    init {
        search.subscribe { ss ->
            val apps = when (ss) {
                SearchState.Disabled -> allApps
                is SearchState.Enabled -> allApps.filter {
                    val (f, t) = parseInstallTimeAndSearch(ss.text)
                    it.match(t) && f(it.firstInstallTime)
                }
            }
            Napier.i { "Apps size: ${apps.size}" }
            items.onNext(ItemsState(apps, ss))
        }
    }

    override fun onSearchNews(newSearch: SearchState) = search.onNext(newSearch)
    fun launch(packageName: String) = news.onNext(Message(appsProvider.launchApp(packageName)))
    fun details(packageName: String) = bsc.screenOpenCallback(Screen.AppInfo(packageName))
    override fun onNews(news: Message) = this.news.onNext(news)

    companion object {
        private const val installTimePref = "\${installTime"
        private val OK_TIME: (Long) -> Boolean = { true }

        fun parseInstallTimeAndSearch(search: String): Pair<(Long) -> Boolean, String> {
            return when {
                search.startsWith(installTimePref) -> {
                    // going to parse string like ${installTime>2022-12-23--18:10:00}
                    val relEnd = search.indexOf("}")
                    if (relEnd == -1) return OK_TIME to search
                    val relStart = installTimePref.length
                    val dtStart = relStart + 1
                    val timestamp = parseDateToUTC(search.substring(dtStart, relEnd)) ?: return OK_TIME to search
                    val utcFilter: (Long) -> Boolean = when (search[relStart]) {
                        '<' -> { { it <  timestamp } }
                        '=' -> { { it == timestamp } }
                        '>' -> { { it >  timestamp } }
                        else -> OK_TIME
                    }
                    return utcFilter to search.substring(relEnd + 1)
                }
                else -> OK_TIME to search
            }
        }

        private val formatter = SimpleDateFormat("yyyy-MM-dd--HH:mm:ss", Locale.US)

        private fun parseDateToUTC(dateString: String): Long? {
            val date = kotlin.runCatching { formatter.parse(dateString) }.getOrNull() ?: return null
            return date.time
        }

        private fun prettifyUTC(utc: Long): String {
            val date = Date(utc)
            return formatter.format(date)
        }

        private fun timestampToSearchState(timestamp: Long): SearchState {
            return when (timestamp) {
                0L -> SearchState.Disabled
                else -> SearchState.Enabled("$installTimePref>${prettifyUTC(timestamp)}}")
            }
        }
    }
}