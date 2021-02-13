package com.moose.foodies.features.feature_ingredients.domain

fun List<MissedIngredient>.clean(): List<MissedIngredient> {
    return this.toSet().toList()
}