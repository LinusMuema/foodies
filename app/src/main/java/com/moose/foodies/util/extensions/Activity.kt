package com.moose.foodies.util.extensions

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Use the reified keyword to ensure that the function receives a Generic type class as a parameter
 * inline tells the compiler to replace every call of the function with the actual implementation of it in the byte code
*/
internal inline fun <reified T> Activity.pop(noinline intentExtras: ((Intent) -> Unit)? = null) {
    val intent = Intent(this, T::class.java)
    intentExtras?.run {
        intentExtras(intent)
    }
    startActivity(intent)
    finish()
}

internal inline fun <reified T> Activity.push(noinline intentExtras: ((Intent) -> Unit)? = null) {
    val intent = Intent(this, T::class.java)
    intentExtras?.run {
        intentExtras(intent)
    }
    startActivity(intent)
}

fun Activity.hideKeyPad(): Boolean {
    return (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow((currentFocus ?: View(this)).windowToken, 0)
}