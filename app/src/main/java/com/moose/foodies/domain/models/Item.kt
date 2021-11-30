package com.moose.foodies.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Item(
    @PrimaryKey
    val _id: String,
    val name: String,
    val type: String,
    val image: String
)