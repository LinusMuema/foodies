package com.moose.foodies.local

import androidx.room.TypeConverter
import com.moose.foodies.features.feature_home.Info
import com.moose.foodies.features.feature_home.Instructions
import com.moose.foodies.features.feature_home.Recipe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun recipeListToJson(recipes: List<Recipe>): String = Json.encodeToString(recipes)

    @TypeConverter
    fun recipeListFromJson(json: String): List<Recipe> = Json.decodeFromString(json)

    @TypeConverter
    fun infoToJson(info: Info): String = Json.encodeToString(info)

    @TypeConverter
    fun infoFromJson(json: String): Info = Json.decodeFromString(json)

    @TypeConverter
    fun instructionsToJson(instructions: Instructions): String = Json.encodeToString(instructions)

    @TypeConverter
    fun instructionsFromJson(json: String): Instructions = Json.decodeFromString(json)
}