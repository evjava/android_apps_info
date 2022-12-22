package com.evjava.apps_info.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Launch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evjava.apps_info.impl.data.AppItem

@Composable
fun AppItemUI(appItem: AppItem, launchCallback: (String) -> Unit, detailsCallback: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .clickable { detailsCallback(appItem.appPackage) }
    ) {
        Row {
            Column {
                AppRowUI(appItem = appItem, fullTitle = false, launchCallback = launchCallback)
            }
        }
    }
}