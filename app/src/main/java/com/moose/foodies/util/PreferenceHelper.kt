package com.moose.foodies.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

object PreferenceHelper {

    private const val tokenKey = "ACCESS_TOKEN"
    private const val loggedKey = "LOGGED_IN"
    private const val widthKey = "DEVICE_WIDTH"
    private const val searchesKey = "RECENT_SEARCHES"
    private const val tapTargetKey = "TAP_TARGET"

    @JvmStatic
    fun defaultPrefs(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }


    fun getAccessToken(context: Context) = defaultPrefs(context).getString(tokenKey, "")

    fun getLoggedStatus(context: Context) = defaultPrefs(context).getBoolean(loggedKey, false)

    fun getDeviceWidth(context: Context) = defaultPrefs(context).getFloat(widthKey, 360f)

    fun getRecentSearches(context: Context) = defaultPrefs(context).getString(searchesKey, "")

    fun getTapTargetStatus(context: Context) = defaultPrefs(context).getBoolean(tapTargetKey, true)

    fun setAccessToken(context: Context, token: String) = defaultPrefs(context).set(tokenKey, token)

    fun setLogged(context: Context, logged: Boolean) = defaultPrefs(context).set(loggedKey, logged)

    fun setDeviceWidth(context: Context, width: Float) = defaultPrefs(context).set(widthKey, width)

    fun setRecentSearches(context: Context, searches: String) = defaultPrefs(context).set(searchesKey, searches)

    fun setTapTargetStatus(context: Context, status: Boolean) = defaultPrefs(context).set(tapTargetKey, status)

    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { this.putString(key, value) }
            is Int -> edit { this.putInt(key, value) }
            is Boolean -> edit { this.putBoolean(key, value) }
            is Float -> edit { this.putFloat(key, value) }
            is Long -> edit { this.putLong(key, value) }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }
}