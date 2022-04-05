package com.moose.foodies.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDTO(
    val name: String,
    val time: String,
    val image: String,
    val description: String,
    val steps: List<String>,
    val equipment: List<String>,
    val categories: List<String>,
    val ingredients: List<String>,
)