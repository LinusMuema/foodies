package com.moose.foodies.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Profile(
    @PrimaryKey
    val _id: String = "",
    val email: String = "",
    val avatar: String = "",
    val username: String = "",
    val description: String = "",
    var current: Boolean = false,
    var favorites: List<String> = listOf(),
)