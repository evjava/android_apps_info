package com.evjava.apps_info.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evjava.apps_info.impl.data.AppItem

// todo fix duplication here and in AppItemsUI
@Composable
fun AppDetailedItemUI(modifier: Modifier = Modifier, appItem: AppItem, launchCallback: (String) -> Unit, detailsCallback: (String) -> Unit) {
    Card(
        modifier = modifier
            .padding(2.dp)
            .clickable { detailsCallback(appItem.appPackage) }
    ) {
        Row {
            Column {
                AppRowUI(appItem = appItem, fullTitle = true, launchCallback = launchCallback)
                Text(text = "version: ${appItem.appVersion}", fontSize = 15.sp)
                Text(text = "package: ${appItem.appPackage}", fontSize = 15.sp)
//                Text(text = "SD: ${i.appSourceDir}", fontSize = 14.sp)
                Text(text = "SHA-1: ${appItem.appApkSHA1}", fontSize = 13.sp)
            }
        }
    }
}