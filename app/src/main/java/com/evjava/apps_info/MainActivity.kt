package com.evjava.apps_info

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import com.evjava.apps_info.api.ApperContextI
import com.evjava.apps_info.api.AppsProviderI
import com.evjava.apps_info.impl.AppsProvider
import com.evjava.apps_info.ui.compose.RootContent
import com.evjava.apps_info.ui.navigation.RootComponent
import com.evjava.apps_info.ui.navigation.RootComponentI
import com.evjava.apps_info.ui.theme.Theme

class MainActivity : ComponentActivity(), ApperContextI {
    lateinit var rootComponent: RootComponentI

    override val appsProvider: AppsProviderI by lazy { AppsProvider(packageManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rootComponent = RootComponent(this, defaultComponentContext())

        setContent {
            RootContent(component = rootComponent)
        }
    }
}