package com.evjava.apps_info.impl

import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.observable.subscribe
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import com.evjava.apps_info.api.BaseScreenContext
import com.evjava.apps_info.api.ScreenControllerI
import com.evjava.apps_info.api.SearchControllerI
import com.evjava.apps_info.api.SearchState
import com.evjava.apps_info.api.data.ItemsState
import com.evjava.apps_info.ui.navigation.Screen
import com.evjava.apps_info.utils.RxUtils.wrapObservable
import io.github.aakira.napier.Napier

class AppsListController(bsc: BaseScreenContext, screen: Screen.AppsList) : ScreenControllerI, SearchControllerI {
    override val title: Observable<String> = "Apps".wrapObservable
    override val items = BehaviorSubject(ItemsState.EMPTY)
    override val search = BehaviorSubject<SearchState>(SearchState.Disabled)

    val allApps = bsc.appsProvider.getApps(SearchState.Disabled)

    init {
        search.subscribe { ss ->
            val apps = when (ss) {
                SearchState.Disabled -> allApps
                is SearchState.Enabled -> allApps.filter {
                    val t = ss.text
                    (t.isEmpty()) || it.appName.contains(t) || it.appPackage.contains(t) || it.appVersion.contains(t)
                }
            }
            Napier.i { "Apps size: ${apps.size}" }
            items.onNext(ItemsState(apps, ss))
        }
    }

    override fun onSearchNews(newSearch: SearchState) {
        search.onNext(newSearch)
    }
}