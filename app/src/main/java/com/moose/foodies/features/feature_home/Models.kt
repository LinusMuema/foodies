package com.moose.foodies.features.feature_home

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Recipes")
data class HomeData(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val joke: String,
    val recipes: List<Recipe>,
    val trivia: String
)

@Entity(tableName = "Favorites")
data class Recipe(
    @PrimaryKey val id: Int,
    val info: Info,
    val instructions: Instructions,
)

data class Info(
    val image: String,
    val title: String,
)

data class Instructions(
    val equipment: List<Item>,
    val ingredients: List<Item>,
    val sections: List<Section>,
)

data class Item(
    val id: Int,
    val image: String,
    val localizedName: String,
    val name: String,
)

data class Section(
    val name: String,
    val steps: List<Step>,
)

data class Step(
    val instruction: String,
    val number: Int,
)