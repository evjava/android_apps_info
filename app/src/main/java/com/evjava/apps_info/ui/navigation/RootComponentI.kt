package com.evjava.apps_info.ui.navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.evjava.apps_info.api.ScreenControllerI

interface RootComponentI {
    fun registerBackForSearch(callback: () -> Boolean)
    val childStack: Value<ChildStack<*, ScreenControllerI>>
}