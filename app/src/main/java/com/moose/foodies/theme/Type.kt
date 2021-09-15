package com.moose.foodies.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.moose.foodies.R

private val Poppins = FontFamily(
    Font(R.font.bold, FontWeight.Bold),
    Font(R.font.light, FontWeight.Light),
    Font(R.font.regular, FontWeight.Normal),
    Font(R.font.thin, FontWeight.ExtraLight)
)

val typography = Typography(
    h4 = TextStyle(
        fontSize = 30.sp,
        lineHeight = 37.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
    ),
    h5 = TextStyle(
        fontSize = 24.sp,
        lineHeight = 29.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
    ),
    h6 = TextStyle(
        fontSize = 20.sp,
        lineHeight = 24.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
    ),
    body1 = TextStyle(
        fontSize = 16.sp,
        lineHeight = 28.sp,
        fontFamily = Poppins,
        letterSpacing = 0.15.sp,
        fontWeight = FontWeight.Normal,
    ),
    body2 = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = Poppins,
        letterSpacing = 0.25.sp,
        fontWeight = FontWeight.Normal,
    ),
    button = TextStyle(
        fontSize = 14.sp,
        lineHeight = 16.sp,
        fontFamily = Poppins,
        letterSpacing = 1.25.sp,
        fontWeight = FontWeight.Normal,
    ),
    caption = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontFamily = Poppins,
        letterSpacing = 0.4.sp,
        fontWeight = FontWeight.Light,
    ),
)