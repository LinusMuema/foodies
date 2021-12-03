package com.moose.foodies.presentation.features.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun Explore(controller: NavController){
    Column {
        Text(
            text = "Discover more recipes",
            style = typography.h5.copy(color = colors.primary)
        )
    }
}