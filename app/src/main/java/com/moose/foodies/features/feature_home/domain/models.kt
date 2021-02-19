package com.moose.foodies.features.feature_home.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class RelayResponse(val message: String)

@Entity(tableName = "Recipes")
@Serializable
data class HomeData(    
    @PrimaryKey
    val id: Int = 1,
    val joke: String,
    val recipes: List<Recipe>,
    val trivia: String
)

@Entity(tableName = "Favorites")
@Serializable
data class Recipe(
    @PrimaryKey val id: Int,
    val info: Info,
    val instructions: Instructions,
)

@Serializable
data class Info(
    val image: String,
    val title: String,
)

@Serializable
data class Instructions(
    val equipment: List<Item>,
    val ingredients: List<Item>,
    val sections: List<Section>,
)

@Serializable
data class Item(
    val id: Int,
    val image: String,
    val localizedName: String,
    val name: String,
)

@Serializable
data class Section(
    val name: String,
    val steps: List<Step>,
)

@Serializable
data class Step(
    val instruction: String,
    val number: Int,
)