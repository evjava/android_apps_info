package com.evjava.apps_info

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.blankj.utilcode.util.AppUtils
import com.evjava.apps_info.api.SearchState
import com.evjava.apps_info.impl.data.AppItem


object PackageManagerExt {
    fun PackageManager.getApps(search: SearchState): List<AppItem> {
        val infos: List<ApplicationInfo> = this.getInstalledApplications(PackageManager.GET_META_DATA)

        // todo search
        return infos.mapNotNull { this.getByPackageName(it.packageName) }
    }

    fun PackageManager.getByPackageName(packageName: String): AppItem? {
        val pi: PackageInfo = this.getPackageInfo(packageName, 0)

        val appSourceDir = pi.applicationInfo.sourceDir
        val appSignaturesSHA1 = AppUtils.getAppSignaturesSHA1(packageName)
        return if (this.getLaunchIntentForPackage(packageName) != null) {
            AppItem(
                icon = this.getApplicationIcon(packageName),
                appName = pi.applicationInfo.loadLabel(this).toString(),
                appPackage = packageName,
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
