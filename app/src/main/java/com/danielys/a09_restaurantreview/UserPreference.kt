package com.danielys.a09_restaurantreview

import android.content.Context

internal class UserPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val DAYLIGHT = "daylight"
    }
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    fun setDaylightSet(value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(DAYLIGHT, value)
        editor.apply()
    }
    fun getDaylightSet(): Boolean {
        return preferences.getBoolean(DAYLIGHT, false)
    }
}