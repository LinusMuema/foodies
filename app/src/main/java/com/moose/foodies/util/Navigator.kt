package com.moose.foodies.util

import android.app.Activity
import android.content.Intent
import com.moose.foodies.features.home.HomeActivity
import kotlin.reflect.KClass

/*
    * Use the reified keyword to ensure that the function receives a Generic type class as a
      parameter

    * inline tells the compiler to replace every call of the function with the actual implementation
      of it in the byte code
*/
internal inline fun <reified T> Activity.push(noinline intentExtras: ((Intent) -> Unit)? = null) {
    val intent = Intent(this, T::class.java)
    intentExtras?.run {
        intentExtras(intent)
    }
    startActivity(intent)
}

internal inline fun <reified T> Activity.pushWithoutHistory(noinline intentExtras: ((Intent) -> Unit)? = null) {
    val intent = Intent(this, T::class.java)
    intentExtras?.run {
        intentExtras(intent)
    }
    startActivity(intent)
    finish()
}