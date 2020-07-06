package com.moose.foodies.util

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

object HeightCalculator  {
    fun getImageHeight(context: Context) = PreferenceHelper.getDeviceWidth(context) / 1.62
}