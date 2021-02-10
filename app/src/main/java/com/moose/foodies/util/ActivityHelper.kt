package com.moose.foodies.util

import android.app.Activity
import android.content.pm.ActivityInfo
import com.moose.foodies.util.extensions.hideBottomBar


object ActivityHelper {
    fun initialize(activity: Activity) {
        activity.hideBottomBar()
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

}