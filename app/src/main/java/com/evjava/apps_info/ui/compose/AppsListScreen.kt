package com.evjava.apps_info.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.evjava.apps_info.api.data.Item
import com.evjava.apps_info.api.data.ItemsState
import com.evjava.apps_info.impl.AppsListController
import com.evjava.apps_info.impl.data.AppItem
import com.evjava.apps_info.ui.compose.ComposeUtil.subscribeAsNews
import com.evjava.apps_info.ui.compose.ComposeUtil.subscribeAsState
import com.evjava.apps_info.ui.compose.utils.VerticalFastScroller

@Composable
fun AppsListScreen(si: AppsListController) {
    val itemsState by si.items.subscribeAsState(initState = ItemsState.EMPTY)
    val news by si.news.subscribeAsNews()

    Column {
        Row(modifier = Modifier.padding(end = 7.dp, top = 3.dp)) {
            Text(modifier = Modifier.weight(1f), text = "")
            Text(text = "# ${itemsState.items.size}")
        }

        val listState = rememberLazyListState()
        VerticalFastScroller(listState = listState, isReversed = false) {
            LazyColumn(
                modifier = Modifier
                    .padding(3.dp)
                    .weight(1f)
                    .fillMaxWidth(),
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

        Spacer(modifier = Modifier.weight(1f))
        Row {
            Text(text = news?.text ?: "")
        }
    }
}