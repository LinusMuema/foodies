package com.moose.foodies.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Profile(
    @PrimaryKey
    val _id: String,
    val email: String,
    val username: String,
    val favorites: List<Recipe>
)