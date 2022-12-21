package com.evjava.apps_info.impl.data

import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable
import com.evjava.apps_info.api.data.Item

data class AppItem(
    val appInfo: ApplicationInfo,
    val icon: Drawable,
    val appName: String,
    val appPackage: String,
    val appVersion: String,
    val appApkSHA1: String
) : Item {
    companion object {
//        val DEMO = listOf(
////            AppItem(ApplicationInfo.CATEGORY_ACCESSIBILITY)
//        )
    }
}