package com.moose.foodies.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class SearchData(
    val name: String,
    val categories: List<String>
)
