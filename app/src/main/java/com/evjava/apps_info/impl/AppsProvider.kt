package com.evjava.apps_info.impl

import android.content.Intent
import android.content.pm.PackageManager
import com.evjava.apps_info.PackageManagerExt.getApps
import com.evjava.apps_info.PackageManagerExt.getByPackageName
import com.evjava.apps_info.api.AppsProviderI
import com.evjava.apps_info.impl.data.AppItem
import io.github.aakira.napier.Napier

class AppsProvider(val pm: PackageManager, val launchCallback: (Intent) -> Unit) : AppsProviderI {
    override fun getApps(): List<AppItem> = pm.getApps()
    override fun getByPackageName(packageName: String): AppItem? = pm.getByPackageName(packageName)

    override fun launchApp(packageName: String): String {
        val i: Intent? = pm.getLaunchIntentForPackage(packageName)
        Napier.i { "Launch intent: $i" }
        return when (i) {
            null -> "Can't launch package \"$packageName\""
            else -> "Launched: \"$packageName\"".apply { launchCallback(i) }
        }
    }

}