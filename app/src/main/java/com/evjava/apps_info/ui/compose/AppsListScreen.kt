package com.evjava.apps_info.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.evjava.apps_info.api.data.Item
import com.evjava.apps_info.api.data.ItemsState
import com.evjava.apps_info.impl.AppsListController
import com.evjava.apps_info.impl.data.AppItem
import com.evjava.apps_info.ui.compose.ComposeUtil.subscribeAsNews
import com.evjava.apps_info.ui.compose.ComposeUtil.subscribeAsState

@Composable
fun AppsListScreen(si: AppsListController) {
    val itemsState by si.items.subscribeAsState(initState = ItemsState.EMPTY)
    var news by si.news.subscribeAsNews()

    Column {
        Row {
            Text(modifier = Modifier.weight(1f), text = "")
            Text(text = "# ${itemsState.items.size}")
        }

        LazyColumn(
            modifier = Modifier
                .padding(3.dp)
                .weight(1f)
                .fillMaxWidth()
            ,
        ) {
            itemsIndexed(itemsState.items) { _, i: Item ->
                when (i) {
                    is AppItem -> AppItemUI(i, si::launch, si::details)
                    else -> Text("Item not implemented")
                }
            }
        }

        Row {
            Text(text = news?.text ?: "")
        }
    }
}