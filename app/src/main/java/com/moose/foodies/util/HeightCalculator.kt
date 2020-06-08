package com.moose.foodies.util

import android.content.SharedPreferences
import javax.inject.Inject

open class HeightCalculator @Inject constructor(private val sharedPreferences: SharedPreferences) {
    open fun getImageHeight() = sharedPreferences.getFloat("deviceWidth", 360f) / 1.62
}