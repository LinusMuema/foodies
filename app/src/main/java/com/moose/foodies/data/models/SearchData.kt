package com.moose.foodies.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SearchData(
    val name: String,
    val categories: List<String>
)
