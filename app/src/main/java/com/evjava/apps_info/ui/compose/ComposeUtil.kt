package com.evjava.apps_info.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.observable.subscribe
import io.github.aakira.napier.Napier
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object ComposeUtil {
    @Composable
    fun <T : Any> Observable<T>.subscribeAsState(initState: T): MutableState<T> {
        val state = remember(this) { mutableStateOf(initState) }

        DisposableEffect(this) {
            val disposable = subscribe {
                try {
                    state.value = it
                } catch (e: Exception) {
                    Napier.e(e) { "something bad happen... initState: $initState" }
                }
            }
            onDispose {
                disposable.dispose()
            }
        }
        return state
    }

    @Composable
    fun <T : Any> Observable<T>.subscribeAsNews(): MutableState<T?> {
        val state = remember(this) { mutableStateOf<T?>(null) }

        DisposableEffect(this) {
            val disposable = subscribe {
                state.value = it
            }
            onDispose {
                disposable.dispose()
            }
        }
        return state
    }

    @Composable
    fun <T> ReadWriteProperty<Any?, T>.memo(callback: ((T) -> Unit)? = null): MutableState<T> {
        var x by this
        val xMemory = remember { mutableStateOf(x) }
        if (xMemory.value != x) {
            x = xMemory.value
            callback?.invoke(x)
        }
        return xMemory
    }

    val <T> MutableState<T>.asProp: ReadWriteProperty<Any?, T>
        get() {
            val state = this
            return object : ReadWriteProperty<Any?, T> {
                override fun getValue(thisRef: Any?, property: KProperty<*>): T {
                    return state.value
                }

                override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
                    state.value = value
                }
            }
        }

    fun <T> MutableState<T?>.consume(): T? {
        return value?.apply { value = null }
    }

    /**
     * adds "- " after inserting "\n" if the beginning of previous before cursor line starts with "- "
     */
    fun postProcessAddDash(oldVal: TextFieldValue, newVal: TextFieldValue): TextFieldValue {
        val cursor = newVal.selection.min
        return if (
            cursor != 0 &&
            cursor == newVal.selection.max &&
            newVal.text[cursor - 1] == '\n' &&
            oldVal.selection.max + 1 == newVal.selection.max &&
            dashSpaceFound(oldVal.text, oldVal.selection.min - 1)
        ) {
            newVal.insertAtCursor("- ")
        } else {
            newVal
        }
    }

    private fun dashSpaceFound(s: CharSequence, start: Int): Boolean {
        val i = s.lastIndexOf('\n', start)
        return i + 2 < s.length && s[i + 1] == '-' && s[i + 2] == ' '
    }

    fun TextFieldValue.insertAtCursor(extra: String): TextFieldValue {
        val cursor = selection.min
        val newText = text.substring(0 until cursor) + extra + text.substring(cursor)
        val newSelection = TextRange(cursor + extra.length)
        return copy(text = newText, selection = newSelection)
    }
}