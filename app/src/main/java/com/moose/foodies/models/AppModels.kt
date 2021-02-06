package com.moose.foodies.models

import androidx.room.Entity
import androidx.room.PrimaryKey


data class AuthResponse(val message: String, val token: String, val type: String, val reason: String)

data class Credentials(val email: String, val password: String)

data class IntolerancesUpdate(val message: String, val updated: Boolean)

data class Intolerances(val intolerances: List<Intolerance>, val message: String)

data class Intolerance(val _id: String, val image_url: String, val name: String)

@Entity(tableName = "Favorites")
data class Recipe(@PrimaryKey val id: Int, val info: Info, val instructions: Instructions)

data class Info(val id: Int, val image: String, val imageType: String, val title: String)

data class Instructions(val equipment: List<InstructionItem>, val ingredients: List<InstructionItem>, val sections: List<Section>)

data class InstructionItem(val id: Int, val image: String, val localizedName: String, val name: String)

data class Section(val _id: String, val name: String, val steps: List<Step>)

data class Step(val _id: String, val instruction: String, val number: Int)

data class RecipeSearch(val message: String, val recipes: List<RecipeResults>, val videos: List<Video>)

data class RecipeResults(val id: Int, val image: String, val openLicense: Int, val readyInMinutes: Int, val servings: Int, val sourceUrl: String, val title: String)

data class Video(val length: Int, val rating: Double, val shortTitle: String, val thumbnail: String, val title: String, val views: Int, val youTubeId: String)

data class FridgeSearch(val message: String, val recipes: List<RecipeSuggestion>)

data class RecipeSuggestion(val id: Int, val image: String, val missedIngredients: List<FridgeIngredient>, val title: String, val usedIngredients: List<FridgeIngredient>)

data class FridgeIngredient(val amount: Double, val extendedName: String, val id: Int, val image: String, val name: String, val original: String, val unit: String, val unitLong: String)

data class FridgeRecipe(val message: String, val instructions: Instructions)
