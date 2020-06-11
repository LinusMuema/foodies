package com.moose.foodies.models

import androidx.room.Entity
import androidx.room.PrimaryKey


data class AuthResponse(val message: String, val token: String, val type: String, val reason: String)

data class Credentials(val email: String, val password: String)

data class UiState(val state: String, val reason: String?)

data class IntolerancesUpdate(val message: String, val updated: Boolean)

data class Intolerances(val intolerances: List<Intolerance>, val message: String)

data class Intolerance(val _id: String, val image_url: String, val name: String)

@Entity
data class Recipes(@PrimaryKey(autoGenerate = true) val id: Int, val message: String, val recipes: List<Recipe>, val joke: String, val trivia: String)

@Entity(tableName = "Favorites")
data class Recipe(@PrimaryKey val id: Int, val info: Info, val instructions: Instructions)

data class Info(val id: Int, val image: String, val imageType: String, val title: String)

data class Instructions(val equipment: List<InstructionItem>, val ingredients: List<InstructionItem>, val sections: List<Section>)

data class InstructionItem(val id: Int, val image: String, val localizedName: String, val name: String)

data class Section(val _id: String, val name: String, val steps: List<Step>)

data class Step(val _id: String, val instruction: String, val number: Int)