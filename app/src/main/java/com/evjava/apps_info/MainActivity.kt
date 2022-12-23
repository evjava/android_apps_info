package com.evjava.apps_info

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.evjava.apps_info.api.ApperContextI
import com.evjava.apps_info.api.AppsProviderI
import com.evjava.apps_info.impl.AppsProvider
import com.evjava.apps_info.ui.compose.RootContent
import com.evjava.apps_info.ui.navigation.RootComponent
import com.evjava.apps_info.ui.navigation.RootComponentI
import io.github.aakira.napier.Napier

class MainActivity : ComponentActivity(), ApperContextI {
    private lateinit var rootComponent: RootComponentI

    override val appsProvider: AppsProviderI by lazy { AppsProvider(packageManager) {
        this.startActivity(it)
    } }

    override fun exit() = finish()

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

        startService(Intent(this, AppListenerService::class.java))
        setContent {
            RootContent(component = rootComponent)
        }
    }

    companion object {
        val SHOW_INSTALLED_KEY = "SHOW_INSTALLED"
        val TIMESTAMP_KEY = "TIMESTAMP"
    }
}