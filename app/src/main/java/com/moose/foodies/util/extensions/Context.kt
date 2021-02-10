package com.moose.foodies.util.extensions

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.moose.foodies.R

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

fun Context.showToast(message: String?){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.shareRecipe(name: String, id: Int){
    val message = getString(R.string.share_recipe, name, id)
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message)
        type = "text/plain"
    }
    startActivity(Intent.createChooser(intent, "Share this recipe via:"))
}