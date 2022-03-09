package com.moose.foodies.presentation.features.home.fridge

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
@ExperimentalAnimationApi
fun Fridge(){
    var open by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()){
        AnimatedContent(
            targetState = open,
            transitionSpec = { slideInVertically() + fadeIn() with slideOutVertically() + fadeOut() }
        ) {
            if (it) FilterPage(onClose = { open = false })
            else ResultsPage(onOpen = { open = true})
        }
    }
}

@Composable
fun FilterPage(onClose: () -> Unit){
    Box(modifier = Modifier.fillMaxSize().background(Color.Cyan).clickable { onClose() }){
    }
}

@Composable
fun ResultsPage(onOpen: () -> Unit){
    Box(modifier = Modifier.fillMaxSize().padding(10.dp)){
        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            FloatingActionButton(onClick = { onOpen() }) {
                Icon(Icons.Default.Add, contentDescription = "fab icon")
            }
        }
    }
}