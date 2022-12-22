package com.evjava.apps_info.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.evjava.apps_info.impl.AppInfoController
import com.evjava.apps_info.ui.compose.ComposeUtil.subscribeAsNews

@Composable
fun AppInfoScreen(si: AppInfoController) {
    val news by si.news.subscribeAsNews()

    Column {
        AppDetailedItemUI(modifier = Modifier.weight(1f), si.app, si::launch) {}
        Row {
            Text(modifier = Modifier.fillMaxWidth(), text = news?.text ?: "")
        }
    }
}