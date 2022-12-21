package com.evjava.apps_info.ui.compose

import androidx.compose.runtime.Composable
import com.evjava.apps_info.impl.AppInfoController

@Composable
fun AppInfoScreen(si: AppInfoController) {
    val app = si.app

    AppDetailedItemUI(app, si::launch) {}
}