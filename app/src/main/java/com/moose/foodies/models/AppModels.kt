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

data class RecipeSearch(val message: String, val recipes: List<RecipeResults>)

data class RecipeResults(val id: Int, val image: String, val openLicense: Int, val readyInMinutes: Int, val servings: Int, val sourceUrl: String, val title: String)

data class FridgeSearch(val message: String, val recipes: List<FridgeResults>)

data class FridgeResults(val id: Int, val image: String, val missedIngredients: List<MissedIngredient>, val title: String)

data class MissedIngredient(val aisle: String, val amount: Int, val id: Int, val image: String, val meta: List<String>, val metaInformation: List<String>, val name: String, val original: String, val originalName: String, val originalString: String, val unit: String, val unitLong: String, val unitShort: String)