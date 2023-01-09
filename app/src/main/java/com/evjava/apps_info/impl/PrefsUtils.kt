package com.evjava.apps_info.impl

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object PrefsUtils {
    fun SharedPreferences.editApply(callback: SharedPreferences.Editor.() -> Unit) {
        return this.edit().apply(callback).apply()
    }

    fun <E> SharedPreferences.mapPref(key: String, recognizer: (String?) -> E, serializer: (E) -> String) =
        object : ReadWriteProperty<Any?, E> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): E = recognizer(getString(key, null))
            override fun setValue(thisRef: Any?, property: KProperty<*>, value: E) = editApply { putString(key, serializer(value)) }
        }

    fun SharedPreferences.strPref(key: String, default: String) = mapPref(key, { it ?: default }, { it })

    fun SharedPreferences.boolPref(key: String, defaultValue: Boolean) =
        object : ReadWriteProperty<Any?, Boolean> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean = getBoolean(key, defaultValue)
            override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) = editApply { putBoolean(key, value) }
        }
}