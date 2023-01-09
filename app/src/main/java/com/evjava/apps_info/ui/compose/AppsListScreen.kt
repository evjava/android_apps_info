package com.evjava.apps_info.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.evjava.apps_info.api.data.Item
import com.evjava.apps_info.api.data.ItemsState
import com.evjava.apps_info.impl.AppsListController
import com.evjava.apps_info.impl.data.AppItem
import com.evjava.apps_info.ui.compose.ComposeUtil.subscribeAsNews
import com.evjava.apps_info.ui.compose.ComposeUtil.subscribeAsState
import com.evjava.apps_info.ui.compose.utils.VerticalFastScroller
import com.evjava.apps_info.ui.theme.LocalExtColors

@Composable
fun AppsListScreen(si: AppsListController) {
    val itemsState by si.items.subscribeAsState(initState = ItemsState.EMPTY)
    val news by si.news.subscribeAsNews()

    Column(modifier = Modifier.padding(5.dp)) {
        Row(modifier = Modifier.padding(end = 7.dp, top = 3.dp)) {
            Text(modifier = Modifier.weight(1f), text = "")
            Text(text = "# ${itemsState.items.size}")
        }

        Column(modifier = Modifier.weight(1f)) {
            val listState = rememberLazyListState()
            VerticalFastScroller(listState = listState, isReversed = false) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    state = listState,
                ) {
                    itemsIndexed(itemsState.items) { _, i: Item ->
                        when (i) {
                            is AppItem -> AppItemUI(i, si::launch, si::details)
                            else -> Text("Item not implemented")
                        }
                    }
                }
            }
        }

        Divider(color = LocalExtColors.current.border)
        Row {
            Text(text = news?.text ?: "")
        }
    }
}