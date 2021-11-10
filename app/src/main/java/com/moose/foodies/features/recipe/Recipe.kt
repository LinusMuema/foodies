package com.moose.foodies.features.recipe

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Recipe(id: String?) {
    val viewmodel: RecipeViewmodel = hiltViewModel()

    val recipe by viewmodel.recipe.observeAsState()
    val equipment by viewmodel.equipment.observeAsState()
    val ingredients by viewmodel.ingredients.observeAsState()

    id?.let { viewmodel.getRecipe(it) }
}