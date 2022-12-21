package com.evjava.apps_info.api

import com.arkivanov.decompose.ComponentContext
import com.evjava.apps_info.ui.navigation.Screen

interface BaseContextI : ComponentContext, ApperContextI {
    val screenOpenCallback: (Screen) -> Unit
}