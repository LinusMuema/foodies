package com.moose.foodies.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun FoodiesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit){
    val colors = if (darkTheme) darkColors else lightColors
    
    MaterialTheme(
        shapes = shapes,
        colors = colors,
        content = content,
        typography = typography
    )
}