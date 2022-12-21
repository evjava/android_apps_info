package com.evjava.apps_info.ui.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.evjava.apps_info.api.ApperContextI
import com.evjava.apps_info.api.ScreenControllerI
import com.evjava.apps_info.impl.AppInfoController
import com.evjava.apps_info.impl.AppsListController

class RootComponent(ac: ApperContextI, cc: ComponentContext) : RootComponentI, ApperContextI by ac, ComponentContext by cc {
    private val navigation = StackNavigation<Screen>()
    private val router = childStack(
        initialStack = ::initialStack,
        handleBackButton = true,
        childFactory = ::createChild,
        source = navigation,
    )
    override val childStack: Value<ChildStack<*, ScreenControllerI>> = router

    private fun initialStack(): List<Screen> = listOf(Screen.AppsList())

    private fun createChild(screen: Screen, cc: ComponentContext) : ScreenControllerI {
        return when (screen) {
            is Screen.AppsList -> AppsListController(cc, screen)
            is Screen.AppInfo -> AppInfoController(cc, screen)
        }
    }

}