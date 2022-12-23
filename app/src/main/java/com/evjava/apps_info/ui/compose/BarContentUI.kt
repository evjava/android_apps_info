package com.evjava.apps_info.ui.compose

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import com.evjava.apps_info.api.ScreenControllerI
import com.evjava.apps_info.ui.navigation.RootComponentI

@Composable
fun BarContentUI(scaffoldState: ScaffoldState, controller: ScreenControllerI, component: RootComponentI) {
    ToolbarUI(controller) {
        component.registerBackForSearch(it)
    }
}