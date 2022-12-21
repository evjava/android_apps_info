package com.evjava.apps_info

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.blankj.utilcode.util.AppUtils
import com.evjava.apps_info.api.SearchState
import com.evjava.apps_info.impl.data.AppItem

fun PackageManager.getApps(search: SearchState): List<AppItem> {
    val infos: List<ApplicationInfo> = this.getInstalledApplications(PackageManager.GET_META_DATA)

    // todo search
    return infos.mapNotNull {
        val pi = this.getPackageInfo(it.packageName, 0)
        val appSourceDir = pi.applicationInfo.sourceDir
        val appSignaturesSHA1 = AppUtils.getAppSignaturesSHA1(it.packageName)
        if (this.getLaunchIntentForPackage(it.packageName) != null) {
            AppItem(
                appInfo = it,
                icon = this.getApplicationIcon(it),
                appName = it.loadLabel(this).toString(),
                appPackage = it.packageName,
                appVersion = pi.versionName,
                appApkSHA1 = appSignaturesSHA1.joinToString(),
                appSourceDir = appSourceDir,
                canRun = true
            )
        } else {
            null
        }
    }
}