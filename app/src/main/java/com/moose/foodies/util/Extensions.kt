package com.moose.foodies.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.moose.foodies.R
import com.moose.foodies.features.feature_home.domain.Item

fun View.fadeIn() = this.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.fade_in))

fun View.slideUp() = this.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.slide_up))

fun View.hide() { this.visibility = View.GONE }

fun String.formatUrl(): String = this.replace("312x231", "636x393")

fun List<Item>.clean(): List<Item> = this.toSet().toList()

fun View.setImageHeight(){
    val height = HeightCalculator.getImageHeight(this.context)

    this.requestLayout()
    this.layoutParams.height = height
}

fun ViewGroup.setImageHeight(){
    val height = HeightCalculator.getImageHeight(this.context)

    this.requestLayout()
    this.layoutParams.height = height
}

fun Activity.hideBottomBar(){
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
}


fun Activity.hideAllBars(){
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
}

fun Activity.hideKeyPad(): Boolean {
    return (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow((currentFocus ?: View(this)).windowToken, 0)
}

fun Context.showToast(message: String?){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

