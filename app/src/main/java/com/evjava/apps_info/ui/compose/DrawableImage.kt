package com.evjava.apps_info.ui.compose

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.unit.dp
import com.evjava.apps_info.utils.DrawableUtils.drawableToBitmap

@Composable
fun DrawableImage(drawable: Drawable?) {
    val bitmap = drawable?.drawableToBitmap()
    if (bitmap != null) {
        Image(
            modifier = Modifier.size(35.dp).padding(2.dp),
            painter = BitmapPainter(image = bitmap.asImageBitmap()),
            contentDescription = null
        )
    }
}