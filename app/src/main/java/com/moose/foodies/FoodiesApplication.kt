package com.moose.foodies

import android.app.Application
import androidx.core.view.WindowCompat
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class FoodiesApplication: Application(){

    override fun onCreate() {
        super.onCreate()
    }
}