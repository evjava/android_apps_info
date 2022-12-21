package com.evjava.apps_info

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.evjava.apps_info.api.ScreenControllerI
import com.evjava.apps_info.ui.navigation.RootComponentI
import com.evjava.unotes.ui_front.compose.root.ToolbarUI
import kotlinx.coroutines.launch

@Composable
fun BarContentUI(scaffoldState: ScaffoldState, controller: ScreenControllerI, component: RootComponentI) {
    NavigationIconUI(scaffoldState)
    ToolbarUI(controller) {
        // todo fix
//        component.registerBackPressedHandler(BackPressHandlerType.Search, it)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun NavigationIconUI(scaffoldState: ScaffoldState) {
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    Icon(
        imageVector = Icons.Default.Menu,
        tint = Color.Black, // todo fix
        modifier = Modifier
            .clickable {
                scope.launch { scaffoldState.drawerState.open() }
                keyboardController?.hide()
            }
            .padding(start = 5.dp, end = 16.dp),
        contentDescription = ""
    )
}