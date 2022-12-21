package com.evjava.apps_info.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.evjava.apps_info.BooleanExt.doIf
import com.evjava.apps_info.api.ScreenControllerI
import com.evjava.apps_info.api.SearchControllerI
import com.evjava.apps_info.api.SearchState
import com.evjava.apps_info.api.SearchState.Companion.asSearch
import com.evjava.apps_info.ui.compose.ComposeUtil.subscribeAsState

@Composable
fun ToolbarUI(controller: ScreenControllerI, backPressedHandler: (() -> Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        when (controller) {
            is SearchControllerI -> ToolbarUIWithSearch(controller, backPressedHandler)
            else -> ToolbarTextIconsUI(controller) {}
        }
    }
}

@Composable
private fun <T> RowScope.ToolbarUIWithSearch(
    controller: T,
    backPressedHandler: (() -> Boolean) -> Unit
) where T : ScreenControllerI, T : SearchControllerI {
    val searchState by controller.search.subscribeAsState(initState = SearchState.Disabled)
    backPressedHandler {
        searchState.isEnabled.doIf {
            controller.onSearchNews(SearchState.Disabled)
        }
    }
    val isDisabled = remember { mutableStateOf(searchState.isDisabled) }
    isDisabled.value = searchState.isDisabled

    when (val ss = searchState) {
        is SearchState.Enabled -> SearchFieldUI(ss, isDisabled) { text -> controller.onSearchNews(text.asSearch()) }
        else -> ToolbarTextIconsUI(controller) {
            IconUI(Icons.Default.Search) { controller.onSearchNews(it) }
        }
    }
}

@Composable
private fun IconUI(img: ImageVector, callback: (SearchState) -> Unit) {
    Icon(
        modifier = Modifier
            .padding(14.dp)
            .clickable { callback(SearchState.SEARCH) },
        imageVector = img,
        tint = Color.Black, // todo fix
        contentDescription = null
    )
}

@Composable
private fun RowScope.ToolbarTextIconsUI(toolbar: ScreenControllerI, extra: @Composable () -> Unit) {
    val title by toolbar.title.subscribeAsState(initState = "")

    ProvideTextStyle(value = MaterialTheme.typography.h6) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                color = Color.Black, // todo fix
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    }
    extra()
}