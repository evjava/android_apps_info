package com.evjava.apps_info

import android.app.ActivityManager
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

class MainActivity : ComponentActivity(), ApperContextI {
    lateinit var rootComponent: RootComponentI

    override val appsProvider: AppsProviderI by lazy { AppsProvider(packageManager) {
        this.startActivity(it)
        (getSystemService("activity") as? ActivityManager)?.let {

        }
    } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rootComponent = RootComponent(this, defaultComponentContext())

        setContent {
            RootContent(component = rootComponent)
        }
    }
}