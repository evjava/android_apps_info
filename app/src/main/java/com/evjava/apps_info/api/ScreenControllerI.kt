package com.evjava.apps_info.api

import com.badoo.reaktive.observable.Observable
import com.evjava.apps_info.api.data.ItemsState

interface ScreenControllerI {
    val title: Observable<String>

    val items: Observable<ItemsState>
}