package com.evjava.apps_info

import android.app.Application
import com.evjava.apps_info.impl.Prefs
import com.evjava.apps_info.ui.theme.ThemeUtils
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier

class App : Application() {
    private val prefs by lazy { Prefs.withApp(this) }

    override fun onCreate() {
        super.onCreate()
        val logger = if (BuildConfig.DEBUG) DebugAntilog() else EMPTY_ANTILOG
        Napier.base(logger)
        ThemeUtils.applyTheme(prefs.theme)
    }

    companion object {
        val EMPTY_ANTILOG = object : Antilog() {
            override fun isEnable(priority: LogLevel, tag: String?): Boolean = false
            override fun performLog(priority: LogLevel, tag: String?, throwable: Throwable?, message: String?) {}
        }
    }
}