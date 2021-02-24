package com.moose.foodies.util.extensions

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.moose.foodies.R

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