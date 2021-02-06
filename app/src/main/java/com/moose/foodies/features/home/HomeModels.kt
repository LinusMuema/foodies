package com.moose.foodies.features.home

import androidx.room.Entity

@Entity(tableName = "Recipes")
data class HomeData(val joke: String, val recipes: List<Recipe>, val trivia: String)

data class Recipe(val id: Int, val info: Info, val instructions: Instructions)

data class Info(val id: Int, val image: String, val imageType: String, val title: String)

data class Instructions(
    val equipment: List<Equipment>,
    val ingredients: List<Ingredient>,
    val sections: List<Section>,
)

data class Equipment(
    val id: Int,
    val image: String,
    val localizedName: String,
    val name: String,
    val temperature: Temperature,
)

data class Ingredient(val id: Int, val image: String, val localizedName: String, val name: String)

data class Section(val name: String, val steps: List<Step>)

data class Temperature(val number: Int, val unit: String)

data class Step(val instruction: String, val number: Int)