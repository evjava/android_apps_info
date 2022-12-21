package com.evjava.apps_info.impl

import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.subject.publish.PublishSubject
import com.evjava.apps_info.api.ApperContextI
import com.evjava.apps_info.api.BaseScreenContext
import com.evjava.apps_info.api.Message
import com.evjava.apps_info.api.ScreenControllerI
import com.evjava.apps_info.ui.navigation.Screen
import com.evjava.apps_info.utils.RxUtils.wrapObservable

class AppInfoController(bsc: BaseScreenContext, screen: Screen.AppInfo) : ScreenControllerI, ApperContextI by bsc {
    val app = bsc.ac.appsProvider.getByPackageName(screen.packageName)!!

    override val title: Observable<String> = "info: ${app.appName}".wrapObservable
    override val news = PublishSubject<Message>()

    // todo fix duplication
    fun launch(packageName: String) {
        val status = appsProvider.launchApp(packageName)
        news.onNext(Message(status))
    }
}