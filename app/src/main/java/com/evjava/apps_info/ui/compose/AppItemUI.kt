package com.evjava.apps_info.ui.compose

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evjava.apps_info.impl.data.AppItem
import com.evjava.apps_info.utils.DrawableUtils.drawableToBitmap

@Composable
fun AppItemUI(i: AppItem) {
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
                }
                Text(text = "version: ${i.appVersion}", fontSize = 14.sp)
                Text(text = "package: ${i.appPackage}", fontSize = 14.sp)
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