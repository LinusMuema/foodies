package com.moose.foodies.util.extensions

import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.moose.foodies.R
import com.moose.foodies.util.HeightCalculator

fun View.fadeIn() = this.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.fade_in))

fun View.slideUp() = this.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.slide_up))

fun View.hide() { this.visibility = View.GONE }

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
