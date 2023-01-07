package com.evjava.apps_info.ui.theme

import androidx.appcompat.app.AppCompatDelegate
import io.github.aakira.napier.Napier

object ThemeUtils {
    private const val LIGHT_MODE = "light"
    private const val DARK_MODE = "dark"
    private const val AUTO_MODE = "battery"

    fun applyTheme(theme: String) {
        Napier.i { "Applying theme: $theme" }
        val mode = when (theme) {
            LIGHT_MODE -> AppCompatDelegate.MODE_NIGHT_NO
            DARK_MODE -> AppCompatDelegate.MODE_NIGHT_YES
            AUTO_MODE -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}