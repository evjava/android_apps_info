package com.evjava.apps_info

object BooleanExt {
    inline fun Boolean.doIf(callback: () -> Unit) = apply { if (this) callback() }
}