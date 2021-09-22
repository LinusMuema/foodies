package com.moose.foodies

import android.app.Application
import android.content.Context
import androidx.core.view.WindowCompat
import dagger.hilt.android.HiltAndroidApp
import io.sentry.Sentry

@HiltAndroidApp
open class FoodiesApplication: Application(){

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit  var appContext: Context
    }
}