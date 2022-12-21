package com.evjava.apps_info.api

import com.arkivanov.decompose.ComponentContext
import com.evjava.apps_info.ui.navigation.Screen

open class BaseScreenContext(
    val screen: Screen,
    val ac: ApperContextI,
    val cc: ComponentContext
) : ApperContextI by ac, BaseContextI, ComponentContext by cc