package com.evjava.apps_info.impl

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.evjava.apps_info.App
import com.evjava.apps_info.api.PrefsI
import com.evjava.apps_info.impl.PrefsUtils.strPref

class Prefs(val app: App): PrefsI {
    private val sharedPrefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(app.applicationContext)

    override var theme by sharedPrefs.strPref(PREF_THEME, "default")

    companion object {
        lateinit var instance: Prefs
            private set
        fun withApp(app: App): Prefs {
            if (!Companion::instance.isInitialized) {
                instance = Prefs(app)
            }
            return instance
        }

        val PREF_THEME = "pref_theme"
    }
}