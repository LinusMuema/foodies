package com.moose.foodies.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun FoodiesTheme(content: @Composable () -> Unit){
    val colors = if (isSystemInDarkTheme()) darkColors else lightColors

    MaterialTheme(
        shapes = shapes,
        colors = colors,
        content = content,
        typography = typography
    )
}