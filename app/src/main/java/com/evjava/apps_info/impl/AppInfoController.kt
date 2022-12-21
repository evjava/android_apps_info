package com.evjava.apps_info.impl

import com.arkivanov.decompose.ComponentContext
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import com.evjava.apps_info.api.BaseScreenContext
import com.evjava.apps_info.api.ScreenControllerI
import com.evjava.apps_info.api.data.ItemsState
import com.evjava.apps_info.ui.navigation.Screen
import com.evjava.apps_info.utils.RxUtils.wrapObservable

class AppInfoController(bsc: BaseScreenContext, screen: Screen.AppInfo) : ScreenControllerI {
    override val title: Observable<String> = "info: ${screen.app}".wrapObservable

    val items0 = BehaviorSubject(ItemsState.EMPTY)

    override val items: Observable<ItemsState> = items0
}