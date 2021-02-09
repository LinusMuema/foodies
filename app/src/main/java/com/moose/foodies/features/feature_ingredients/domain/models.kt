package com.moose.foodies.features.feature_ingredients.domain

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val missedIngredients: List<MissedIngredient>,
)

@Serializable
data class MissedIngredient(val image: String, val amount: Double, val name: String, val unitShort: String)
