package com.learnbae.my.data.storage.preferences

import android.content.SharedPreferences

class BooleanPreference (
        private val preference: SharedPreferences,
        private val key: String
    ) {
        companion object {
            private const val DEFAULT_VALUE = false
        }

        fun get():Boolean? {
            return if (isSet()) getValue() else null
        }

        private fun isSet() = preference.contains(key)

        fun getValue(): Boolean {
            return requireNotNull(preference.getBoolean(key, DEFAULT_VALUE))
        }

        fun set(value: Boolean) = preference.edit().putBoolean(key, value).apply()

        fun delete() {
            preference.edit().remove(key).apply()
        }
    }
