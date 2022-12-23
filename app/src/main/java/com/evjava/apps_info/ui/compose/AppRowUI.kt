package com.evjava.apps_info.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evjava.apps_info.impl.data.AppItem
import com.evjava.apps_info.ui.theme.LocalExtColors

@Composable
fun AppRowUI(appItem: AppItem, fullTitle: Boolean, launchCallback: (String) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        DrawableImage(drawable = appItem.icon)
        val maxLines = if (fullTitle) 100 else 1
        Text(modifier = Modifier.weight(1f), maxLines = maxLines, text = appItem.appName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        IconButton(modifier = Modifier.background(MaterialTheme.colors.primary).size(35.dp), onClick = { launchCallback(appItem.appPackage) }) {
            Icon(imageVector = Icons.Default.RocketLaunch, tint = LocalExtColors.current.icon, contentDescription = null)
        }
    }
}

@Preview
@Composable
fun AppRowUITest() {
    val appItem = AppItem(
        icon = null,
        appName = "Some app with very long title such long that it takes all area",
        appPackage = "pack.test",
        appVersion = "1.0",
        appApkSHA1 = "sha-1-la-la-la",
        appSourceDir = "/test/some-app.apk",
        firstInstallTime = 0L,
    )
    AppRowUI(appItem, false) {}
}