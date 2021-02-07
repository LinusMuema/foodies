package com.moose.foodies.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.moose.foodies.R

fun View.fadeIn() = this.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.fade_in))

fun View.slideUp() = this.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.slide_up))

fun View.show() { this.visibility = View.VISIBLE }

fun View.hide() { this.visibility = View.GONE }

fun View.setHeight(height: Int){
    this.requestLayout()
    this.layoutParams.height = height
}

fun ViewGroup.setHeight(height: Int){
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

fun Activity.hideKeyboard(): Boolean {
    return (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow((currentFocus ?: View(this)).windowToken, 0)
}

fun Fragment.hideKeyboard(): Boolean {
    return (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow((activity?.currentFocus ?: View(context)).windowToken, 0)
}


fun ImageView.loadRoundImage(uri: String){
    val options = RequestOptions()
        .circleCrop()
        .error(R.drawable.image_error)

    Glide.with(this.context).setDefaultRequestOptions(options).load(uri).into(this)
}

fun showSnackbar(view: View, message: String) = Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()

fun ImageView.loadCarouselImage(uri: String){
    val options = RequestOptions()
        .transform(RoundedCorners(25))
        .error(R.drawable.image_error)

    Glide.with(this.context).setDefaultRequestOptions(options).load(uri).into(this)
}

fun Context.showToast(message: String?){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadDrawable(drawable: Int) = Glide.with(this.context).load(ContextCompat.getDrawable(this.context, drawable)).into(this)

fun ImageView.loadImage(uri: String) {
    val options = RequestOptions().error(R.drawable.image_error)
    Glide.with(this.context).setDefaultRequestOptions(options).load(uri).into(this)
}

