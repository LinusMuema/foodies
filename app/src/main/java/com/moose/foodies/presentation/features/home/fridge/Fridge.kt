package com.moose.foodies.presentation.features.home.fridge

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi

@Composable
@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun Fridge(){
    val viewmodel: FridgeViewmodel = hiltViewModel()
    val ingredients by remember { viewmodel.ingredients }

    var open by remember { mutableStateOf(true) }

    val spec = slideInVertically() + fadeIn() with slideOutVertically() + fadeOut()
    Box(modifier = Modifier.fillMaxSize().padding(10.dp)){
        AnimatedContent(targetState = open, transitionSpec = { spec }) {
            if (it) FilterPage(onClose = { open = false }, ingredients = ingredients)
            else ResultsPage(onOpen = { open = true })
        }
    }
}