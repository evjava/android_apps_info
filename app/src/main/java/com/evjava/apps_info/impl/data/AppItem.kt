package com.evjava.apps_info.impl.data

import android.graphics.drawable.Drawable
import com.evjava.apps_info.api.data.Item
import com.evjava.apps_info.utils.FileSHA1Code

data class AppItem(
    val icon: Drawable?,
    val appName: String,
    val appPackage: String,
    val appVersion: String,
    val appSourceDir: String,
    val firstInstallTime: Long,
) : Item {
    fun match(t: String): Boolean {
        return (t.isEmpty()) || listOf(appName, appPackage, appVersion).any { it.contains(t, true) }
    }

    val appApkSHA1: String
        get() = FileSHA1Code.sha1Code(appSourceDir)
}