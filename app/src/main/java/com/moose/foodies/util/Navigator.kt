package com.moose.foodies.util

import android.app.Activity
import android.content.Intent

fun Activity.push(activity: Class<out Activity>) {
    this.startActivity(Intent(this, activity::class.java))
}

fun Activity.pushWithoutHistory(activity: Class<out Activity>){
    this.startActivity(Intent(this, activity::class.java))
    finish()
}