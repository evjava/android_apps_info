package com.evjava.apps_info.api

import com.evjava.apps_info.impl.data.AppItem

interface AppsProviderI {
    fun getApps(search: SearchState): List<AppItem>
}