package com.evjava.apps_info.api

import com.evjava.apps_info.impl.data.AppItem

interface AppsProviderI {
    fun getApps(): List<AppItem>
    fun launchApp(packageName: String): String
    fun getByPackageName(packageName: String): AppItem?
}