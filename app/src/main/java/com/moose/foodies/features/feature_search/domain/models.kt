package com.moose.foodies.features.feature_search.domain

import kotlinx.serialization.Serializable

@Serializable
data class SearchResults(
    val recipes: List<Recipe>,
    val videos: List<Video>,
)

@Serializable
data class Recipe(
    val id: Int,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val title: String,
)

@Serializable
data class Video(
    val thumbnail: String,
    val shortTitle: String,
    val youTubeId: String,
)