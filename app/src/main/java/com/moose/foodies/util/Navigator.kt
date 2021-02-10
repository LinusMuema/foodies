package com.moose.foodies.util

import android.app.Activity
import android.content.Context
import android.content.Intent

/*
    * Use the reified keyword to ensure that the function receives a Generic type class as a
      parameter

    * inline tells the compiler to replace every call of the function with the actual implementation
      of it in the byte code
*/
internal inline fun <reified T> Context.push(noinline intentExtras: ((Intent) -> Unit)? = null) {
    val intent = Intent(this, T::class.java)
    intentExtras?.run {
        intentExtras(intent)
    }
    startActivity(intent)
}

internal inline fun <reified T> Activity.pop(noinline intentExtras: ((Intent) -> Unit)? = null) {
    val intent = Intent(this, T::class.java)
    intentExtras?.run {
        intentExtras(intent)
    }
    startActivity(intent)
    finish()
}