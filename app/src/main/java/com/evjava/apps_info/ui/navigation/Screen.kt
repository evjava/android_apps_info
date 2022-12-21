package com.evjava.apps_info.ui.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Screen(val name: String) : Parcelable {
    @Parcelize
    data class AppsList(val search: String = "") : Screen("apps_list")

    @Parcelize
    data class AppInfo(val app: String) : Screen("app_info")
}