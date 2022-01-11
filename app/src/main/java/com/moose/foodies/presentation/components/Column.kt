package com.moose.foodies.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CenterColumn(modifier: Modifier = Modifier, content: @Composable () -> Unit){
    val arrangement = Arrangement.Center
    val alignment = Alignment.CenterHorizontally

    Column(modifier.fillMaxSize(), arrangement, alignment){
        content()
    }
}

@Composable
fun ScrollableColumn(modifier: Modifier = Modifier, content: @Composable () -> Unit){
    Column(modifier = Modifier.animateContentSize().verticalScroll(rememberScrollState())) {
        content()
    }
}