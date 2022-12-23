package com.evjava.apps_info.api

interface ApperContextI {
    val appsProvider: AppsProviderI

    fun exit()
}