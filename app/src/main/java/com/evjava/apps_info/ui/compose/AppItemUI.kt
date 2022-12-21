package com.evjava.apps_info.ui.compose

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evjava.apps_info.impl.data.AppItem
import com.evjava.apps_info.utils.DrawableUtils.drawableToBitmap

@Composable
fun AppItemUI(i: AppItem, launchCallback: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
    ) {
        Row {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    DrawableImage(drawable = i.icon)
                    Text(text = i.appName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.weight(1f))
                    if (i.canRun) {
                        Button(onClick = { launchCallback(i.appPackage) }) {
                            Icon(imageVector = Icons.Default.Launch, contentDescription = null)
                        }
                    }
                }
                Text(text = "version: ${i.appVersion}", fontSize = 14.sp)
                Text(text = "package: ${i.appPackage}", fontSize = 14.sp)
                Text(text = "SD: ${i.appSourceDir}", fontSize = 14.sp)
                Text(text = "SHA-1: ${i.appApkSHA1}", fontSize = 11.sp)
            }
        }
    }
}

@Composable
fun DrawableImage(drawable: Drawable) {
    val bitmap = drawable.drawableToBitmap()
    if (bitmap != null) {
        Image(
            modifier = Modifier.width(40.dp),
            painter = BitmapPainter(image = bitmap.asImageBitmap()),
            contentDescription = null
        )
    }
}