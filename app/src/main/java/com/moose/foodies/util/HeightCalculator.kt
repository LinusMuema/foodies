package com.moose.foodies.util

import android.content.Context

object HeightCalculator  {
    fun getImageHeight(context: Context): Int {
        val scale = context.resources.displayMetrics.density
        return ((PreferenceHelper.getDeviceWidth(context) / 1.62) * scale + 0.5f).toInt()
    }
}