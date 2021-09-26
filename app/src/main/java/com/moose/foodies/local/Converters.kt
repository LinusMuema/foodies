package com.moose.foodies.local

import androidx.room.TypeConverter
import com.moose.foodies.models.Recipe
import kotlinx.serialization.*
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class Converters {

    @TypeConverter
    fun  recipesFromJson(json: String): List<Recipe> = Json.decodeFromString(json)

    @TypeConverter
    fun recipesToJson(recipes: List<Recipe>): String = Json.encodeToString(recipes)

}