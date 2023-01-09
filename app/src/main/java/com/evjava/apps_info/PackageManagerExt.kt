package com.evjava.apps_info

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import com.evjava.apps_info.impl.data.AppItem

object PackageManagerExt {
    fun PackageManager.getApps(): List<AppItem> {
        val infos: List<ApplicationInfo> = this.getInstalledApplications(PackageManager.GET_META_DATA)
        return infos
            .mapNotNull { this.getByPackageName(it.packageName) }
            .sortedBy { it.appName }
    }

    fun PackageManager.getByPackageName(packageName: String): AppItem? {
        return kotlin.runCatching { this.getDetailedByPackageName0(packageName) }.getOrNull()
    }

    /**
     * @return (new sequenceNumber) x (list of apps names)
     */
    fun PackageManager.getInstalledAppsFrom(timestamp: Long, sequenceNumber: Int = 0): Pair<Int, List<AppItem>> {
        val (newSequenceNumber, packages) = if (timestamp != 0L && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val cp = this.getChangedPackages(sequenceNumber) ?: return sequenceNumber to emptyList()
            cp.sequenceNumber to cp.packageNames.mapNotNull { this.getByPackageName(it) }
        } else {
            sequenceNumber to getApps()
        }
        return newSequenceNumber to packages.filter { it.firstInstallTime > timestamp }
    }

    private fun PackageManager.getDetailedByPackageName0(packageName: String): AppItem? {
        val pi: PackageInfo = this.getPackageInfo(packageName, 0)

        val appSourceDir = pi.applicationInfo.sourceDir
        return this.getLaunchIntentForPackage(packageName)?.let {
            AppItem(
                icon = this.getApplicationIcon(packageName),
                appName = pi.applicationInfo.loadLabel(this).toString(),
                appPackage = packageName,
                appVersion = pi.versionName,
                appSourceDir = appSourceDir,
                firstInstallTime = pi.firstInstallTime,
            )
        }
    }
}
