package com.evjava.apps_info

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.decompose.defaultComponentContext
import com.evjava.apps_info.api.ApperContextI
import com.evjava.apps_info.api.AppsProviderI
import com.evjava.apps_info.impl.AppsProvider
import com.evjava.apps_info.impl.Prefs
import com.evjava.apps_info.ui.compose.RootContent
import com.evjava.apps_info.ui.navigation.RootComponent
import com.evjava.apps_info.ui.navigation.RootComponentI
import com.evjava.apps_info.ui.theme.ThemeUtils
import io.github.aakira.napier.Napier

class MainActivity : AppCompatActivity(), ApperContextI {
    override val prefs by lazy { Prefs.instance }
    private lateinit var rootComponent: RootComponentI

    override val appsProvider: AppsProviderI by lazy { AppsProvider(packageManager) {
        this.startActivity(it)
    } }

    override fun exit() = finish()
    override fun toggleTheme(): String {
        val newTheme = if (prefs.theme == "dark") "light" else "dark"
        prefs.theme = newTheme
        ThemeUtils.applyTheme(newTheme)
        return "New theme: $newTheme"
    }

    override fun toggleTracker(newIsEnabled: Boolean?): String {
        val isEnabled = newIsEnabled ?: (!prefs.isTrackerEnabled).apply { prefs.isTrackerEnabled = this }
        val status = if (isEnabled) {
            startTracker(); "enabled"
        } else {
            stopTracker(); "disabled"
        }
        return "App tracker: $status"
    }

    private fun foregroundServiceRunning(): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return activityManager.getRunningServices(Int.MAX_VALUE).any {
            AppListenerService::class.java.name == it.service.className
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val timestamp: Long = intent.getLongExtra(TIMESTAMP_KEY, 0)
        Napier.i { "App Started! Going to show apps from: $timestamp" }
        rootComponent = RootComponent(this, defaultComponentContext(), timestamp = timestamp)

        toggleTracker(prefs.isTrackerEnabled)
        setContent {
            RootContent(component = rootComponent)
        }
    }

    private fun startTracker() = startService(Intent(this, AppListenerService::class.java))
    private fun stopTracker() = stopService(Intent(this, AppListenerService::class.java))

    companion object {
        val SHOW_INSTALLED_KEY = "SHOW_INSTALLED"
        val TIMESTAMP_KEY = "TIMESTAMP"
    }
}