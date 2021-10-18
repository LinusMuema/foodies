package com.moose.foodies.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Recipe(
    @PrimaryKey
    val _id: String,
    val name: String,
    val time: String,
    val image: String,
    val user: Profile,
    val description: String,
    val steps: List<String>,
    val equipment: List<Item>,
    val ingredients: List<Item>,
)

@Serializable
data class RawRecipe(
    val name: String,
    val time: String,
    val image: String,
    val description: String,
    val steps: List<String>,
    val equipment: List<String>,
    val ingredients: List<String>,
)