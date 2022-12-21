package com.evjava.apps_info.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.evjava.apps_info.impl.AppInfoController
import com.evjava.apps_info.impl.AppsListController
import com.evjava.apps_info.ui.navigation.RootComponentI
import com.evjava.apps_info.ui.theme.AppsInfoTheme

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootContent(component: RootComponentI) {
    AppsInfoTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                Children(stack = component.childStack) { screen ->
                    when (val si = screen.instance) {
                        is AppsListController -> AppsListScreen(si)
                        is AppInfoController -> AppInfoScreen(si)
                    }
                }
            }
        }
    }
}