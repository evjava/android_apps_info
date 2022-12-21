package com.evjava.apps_info.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.evjava.apps_info.impl.AppInfoController
import com.evjava.apps_info.impl.AppsListController
import com.evjava.apps_info.ui.navigation.RootComponentI
import com.evjava.apps_info.ui.theme.Theme

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootContent(component: RootComponentI) {
    val scaffoldState = rememberScaffoldState()

    Theme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            Column {
                Children(stack = component.childStack) { screen ->
                    Scaffold(
                        topBar = {
                            TopAppBar {
                                BarContentUI(scaffoldState, screen.instance, component)
                            }
                        },
                        content = { pv ->
                            Column(modifier = Modifier.padding(pv)) {
                                when (val si = screen.instance) {
                                    is AppsListController -> AppsListScreen(si)
                                    is AppInfoController -> AppInfoScreen(si)
                                }
                            }
                        }
                    )

                }
            }
        }
    }
}