package com.moose.foodies.features.recipe.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moose.foodies.features.recipe.RecipeViewmodel

@Composable
fun Details(){
    val viewmodel: RecipeViewmodel = hiltViewModel()
    val equipment by viewmodel.equipment.observeAsState()
    val ingredients by viewmodel.ingredients.observeAsState()

    val scroll = rememberScrollState()
    val clip = RoundedCornerShape(topEnd = 45.dp, topStart = 45.dp)

    Column(modifier = Modifier.fillMaxSize().verticalScroll(state = scroll)) {
        Box(modifier = Modifier.fillMaxWidth().clip(shape = clip).background(color = colors.background)) {
        }
    }
}