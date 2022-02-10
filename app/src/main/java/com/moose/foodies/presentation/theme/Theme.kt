package com.moose.foodies.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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

@Composable
fun Status(color: Color = MaterialTheme.colors.background){
    val isDark = MaterialTheme.colors.isLight
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(color = color, darkIcons = isDark)
    }
}