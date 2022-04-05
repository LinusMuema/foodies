package com.moose.foodies.data.models

import kotlinx.serialization.Serializable

@Serializable
data class IngredientsSearch(val ingredients: List<String>)
