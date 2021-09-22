package com.moose.foodies.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@EntryPoint
@InstallIn(SingletonComponent::class)
interface PreferencesHelper {
    fun preferences(): Preferences
}

class Preferences @Inject constructor(@ApplicationContext context: Context) {

    private val tokenKey = "ACCESS_TOKEN"
    private val preferences = context.getSharedPreferences("FOODIES_PREFS", Context.MODE_PRIVATE)

    fun setToken(token: String) = preferences.set(tokenKey, token)

    fun getToken() = preferences.getString(tokenKey, null)

    operator fun SharedPreferences.set(key: String, value: Any?){
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