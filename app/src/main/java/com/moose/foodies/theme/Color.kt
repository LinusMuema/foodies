package com.moose.foodies.theme

import android.annotation.SuppressLint
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

/**
 * [textDark] & [textLight] will take care of text-based graphics. Icons, texts and vectors.
 * [primaryDark] & [primaryLight] will color the background and system components, i.e, the navigation bar and status bars
 * [secondaryDark] & [secondaryLight] will work on the small ui components like borders, buttons
 * [variantDark] & [variantLight] will take care of large surface components like cards
 * */

// Dark colors
val textDark = Color(0xffFBFFFE)
val primaryDark = Color(0xff082032)
val variantDark = Color(0xff2C394B)
val secondaryDark = Color(0xff2C394B)

// Light colors
val textLight = Color(0xff1B1B1E)
val primaryLight = Color(0xffFBFFFE)
val variantLight = Color(0xffEEF0F2)
val secondaryLight = Color(0xffCB4335)

val errorColor = Color(0xffB03A2E)


val lightColors = lightColors(
    error = errorColor,
    onPrimary = textLight,
    primary = primaryLight,
    onSecondary = primaryLight,
    secondary = secondaryLight,
    primaryVariant = variantLight,
)

val darkColors = darkColors(
    error = errorColor,
    onPrimary = textDark,
    primary = primaryDark,
    onSecondary = textDark,
    secondary = secondaryDark,
    primaryVariant = variantDark,
)