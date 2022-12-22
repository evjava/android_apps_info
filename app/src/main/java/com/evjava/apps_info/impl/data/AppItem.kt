package com.evjava.apps_info.impl.data

import android.graphics.drawable.Drawable
import com.evjava.apps_info.api.data.Item

data class AppItem(
    val icon: Drawable?,
    val appName: String,
    val appPackage: String,
    val appVersion: String,
    val appApkSHA1: String,
    val appSourceDir: String,
    val canRun: Boolean,
) : Item