package com.moose.foodies.util.extensions

import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.moose.foodies.R
import com.moose.foodies.util.HeightCalculator

fun View.fadeIn() = this.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.fade_in))

fun View.slideUp() = this.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.slide_up))

fun View.hide() { this.visibility = View.GONE }

fun ImageView.loadImage(image: String){
    this.load(image){
        placeholder(R.drawable.loading)
        error(R.drawable.ic_error)
    }
}

fun ImageView.loadRoundedImage(image: String){
    this.load(image){
        placeholder(R.drawable.loading)
        error(R.drawable.ic_error)
        transformations(RoundedCornersTransformation(topLeft = 10f, topRight = 10f))
    }
}

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