package com.moose.foodies.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class PagedRecipes(
    val page: Int,
    val totalPages: Int,
    val recipes: List<Recipe>,
)
