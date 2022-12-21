package com.evjava.apps_info.impl

import android.content.pm.PackageManager
import com.evjava.apps_info.api.AppsProviderI
import com.evjava.apps_info.api.SearchState
import com.evjava.apps_info.getApps
import com.evjava.apps_info.impl.data.AppItem

class AppsProvider(
    val pm: PackageManager
) : AppsProviderI {
    override fun getApps(search: SearchState): List<AppItem> {
        return pm.getApps(search)
    }
}