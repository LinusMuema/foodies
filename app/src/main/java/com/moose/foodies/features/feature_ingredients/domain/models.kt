package com.moose.foodies.features.feature_ingredients.domain

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val missedIngredients: List<MissedIngredient>,
    val unusedIngredients: List<UnusedIngredient>
)

@Serializable
data class MissedIngredient(val amount: Double, val name: String, val unitShort: String)

@Serializable
data class UnusedIngredient(val amount: Int, val name: String, val unitShort: String)