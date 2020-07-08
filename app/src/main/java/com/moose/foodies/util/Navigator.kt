package com.moose.foodies.util

import android.app.Activity
import android.content.Intent
import com.moose.foodies.features.home.HomeActivity
import kotlin.reflect.KClass

fun Activity.push(activity: Class<out Activity>) {
    this.startActivity(Intent(this, activity::class.java))
}

fun Activity.pushWithoutHistory(activity: KClass<out Activity>) {
    this.startActivity(Intent(applicationContext, activity::class.java))
    finish()
}