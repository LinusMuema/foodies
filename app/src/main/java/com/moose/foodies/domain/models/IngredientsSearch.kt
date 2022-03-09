package com.moose.foodies.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class IngredientsSearch(val ingredients: List<String>)
