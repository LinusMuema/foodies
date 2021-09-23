package com.moose.foodies.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun Home(navController: NavController){
    Column {
        Box {
            Text("Hi moose", style = MaterialTheme.typography.h6)
        }
    }
}