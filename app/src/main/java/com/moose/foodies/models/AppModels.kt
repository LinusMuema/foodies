package com.moose.foodies.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Favorites")
data class Recipe(@PrimaryKey val id: Int, val info: Info, val instructions: Instructions)

data class Info(val id: Int, val image: String, val imageType: String, val title: String)

data class Instructions(val equipment: List<InstructionItem>, val ingredients: List<InstructionItem>, val sections: List<Section>)

data class InstructionItem(val id: Int, val image: String, val localizedName: String, val name: String)

data class Section(val _id: String, val name: String, val steps: List<Step>)

data class Step(val _id: String, val instruction: String, val number: Int)

data class FridgeSearch(val message: String, val recipes: List<RecipeSuggestion>)

data class RecipeSuggestion(val id: Int, val image: String, val missedIngredients: List<FridgeIngredient>, val title: String, val usedIngredients: List<FridgeIngredient>)

data class FridgeIngredient(val amount: Double, val extendedName: String, val id: Int, val image: String, val name: String, val original: String, val unit: String, val unitLong: String)

data class FridgeRecipe(val message: String, val instructions: Instructions)
