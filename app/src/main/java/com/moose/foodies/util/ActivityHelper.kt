package com.moose.foodies.util

import android.app.Activity
import android.content.pm.ActivityInfo


object ActivityHelper {
    fun initialize(activity: Activity) {
        activity.hideBottomBar()
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

}