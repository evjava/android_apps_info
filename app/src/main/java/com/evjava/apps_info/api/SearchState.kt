package com.evjava.apps_info.api

import kotlinx.serialization.Serializable

@Serializable
sealed class SearchState : java.io.Serializable {
    @Serializable
    object Disabled : SearchState()

    @Serializable
    data class Enabled(val text: String) : SearchState() {
        val isEmpty get() = text.isEmpty()
    }

    val textOrNull: String? get() = (this as? Enabled)?.text
    val textOrEmpty: String get() = textOrNull ?: ""
    val isDisabled get() = this is Disabled
    val isEnabled get() = this is Enabled
    val isEnabledAndEmpty get() = (this as? Enabled)?.let { it.text.isEmpty() } ?: false

    fun withText(newText: String) = Enabled(newText)

    fun swapDisabledAndEmpty() = when (this) {
        Disabled -> SEARCH
        is Enabled -> if (isEmpty) Disabled else this
    }

    companion object {
        val SEARCH = Enabled("")

        fun String?.asSearch() = when (this) {
            null -> SearchState.Disabled
            else -> SearchState.Enabled(this)
        }
    }
}