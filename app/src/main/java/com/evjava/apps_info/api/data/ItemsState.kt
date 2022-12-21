package com.evjava.apps_info.api.data

import com.evjava.apps_info.api.SearchState

data class ItemsState(
    val items: List<Item>,
    val search: SearchState,
) {
    companion object {
        val EMPTY = ItemsState(emptyList(), SearchState.Disabled)
    }
}