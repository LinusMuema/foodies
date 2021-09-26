package com.moose.foodies.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Profile(
    @PrimaryKey
    val email: String,
    val username: String,
    val recipes: List<Recipe>,
    val favorites: List<Recipe>
)