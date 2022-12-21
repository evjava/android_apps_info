package com.evjava.apps_info

import android.content.pm.PackageManager
import com.evjava.apps_info.api.SearchState
import com.evjava.apps_info.impl.data.AppItem

fun PackageManager.getApps(search: SearchState): List<AppItem> {
    val infos = this.getInstalledApplications(PackageManager.GET_META_DATA)

    // todo search
    return infos.map {
        val pi = this.getPackageInfo(it.packageName, 0)
        AppItem(
            appInfo = it,
            icon = this.getApplicationIcon(it),
            appName = it.loadLabel(this).toString(),
            appPackage = it.packageName,
            appVersion = pi.versionName,
            appApkSHA1 = "TODO"
        )
    }
}