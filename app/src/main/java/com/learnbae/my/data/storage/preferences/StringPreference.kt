package com.learnbae.my.data.storage.preferences

import android.content.SharedPreferences

open class StringPreference(
    private val preference: SharedPreferences,
    private val key: String
) {
    companion object {
        private const val DEFAULT_VALUE = ""
    }

    fun get(): String? {
        return if (isSet()) getValue() else null
    }

    private fun isSet() = preference.contains(key)

    fun getValue(): String {
        return requireNotNull(preference.getString(key, DEFAULT_VALUE))
    }

    fun set(value: String?) = preference.edit().putString(key, value).apply()

    fun delete() {
        preference.edit().remove(key).apply()
    }
}