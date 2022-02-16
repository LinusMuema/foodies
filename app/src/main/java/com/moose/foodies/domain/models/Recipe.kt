package com.moose.foodies.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Recipe(
    @PrimaryKey
    val _id: String,
    val user: Profile,
    val name: String,
    val time: String,
    val image: String,
    var type: String = "",
    val description: String,
    val steps: List<String>,
    val equipment: List<String>,
    val categories: List<String>,
    val ingredients: List<String>,
)