package com.evjava.apps_info.api

interface ApperContextI {
    val appsProvider: AppsProviderI
    val prefs: PrefsI

    fun toggleTheme(): String
    fun toggleTracker(newIsEnabled: Boolean? = null): String

    fun exit()
}