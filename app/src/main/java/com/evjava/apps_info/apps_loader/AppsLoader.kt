package com.evjava.apps_info.apps_loader

import android.content.pm.PackageManager

class AppsLoader(private val pm: PackageManager) {
    fun loadApps() {
        val infos = pm.getInstalledApplications(PackageManager.GET_META_DATA)
    }
}