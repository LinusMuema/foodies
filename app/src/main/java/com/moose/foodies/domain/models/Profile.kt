package com.moose.foodies.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Profile(
    @PrimaryKey
    @SerialName("_id")
    val id: String = "",
    val email: String = "",
    val avatar: String = "",
    val username: String = "",
    val description: String = "",
    var current: Boolean = false,
    var favorites: List<String> = listOf(),
)