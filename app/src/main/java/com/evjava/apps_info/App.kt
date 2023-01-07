package com.evjava.apps_info

import android.app.Application
import com.evjava.apps_info.impl.Prefs
import com.evjava.apps_info.ui.theme.ThemeUtils
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class App : Application() {
    private val prefs by lazy { Prefs.withApp(this) }

    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
        ThemeUtils.applyTheme(prefs.theme)
    }
}