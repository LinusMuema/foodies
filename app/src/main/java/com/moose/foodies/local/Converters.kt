package com.moose.foodies.local

import androidx.room.TypeConverter
import com.moose.foodies.models.Item
import com.moose.foodies.models.Profile
import com.moose.foodies.models.Recipe
import kotlinx.serialization.*
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class Converters {
    @TypeConverter
    fun  recipesFromJson(json: String): List<Recipe> = Json.decodeFromString(json)

    @TypeConverter
    fun recipesToJson(recipes: List<Recipe>): String = Json.encodeToString(recipes)

    @TypeConverter
    fun profileToJson(profile: Profile): String = Json.encodeToString(profile)

    @TypeConverter
    fun profileFromJson(json: String): Profile = Json.decodeFromString(json)

    @TypeConverter
    fun stringsToJson(strings: List<String>): String = Json.encodeToString(strings)

    @TypeConverter
    fun stringsFromJson(json: String): List<String> = Json.decodeFromString(json)

    @TypeConverter
    fun itemsToJson(items: List<Item>): String = Json.encodeToString(items)

    @TypeConverter
    fun itemsFromJson(json: String): List<Item> = Json.decodeFromString(json)
}