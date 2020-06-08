package com.moose.foodies.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.moose.foodies.models.*

class Converters {
    @TypeConverter
    fun recipeListToJson(recipes: List<Recipe>): String = Gson().toJson(recipes)

    @TypeConverter
    fun recipeListFromJson(json: String): List<Recipe> = Gson().fromJson(json, Array<Recipe>::class.java).toList()

    @TypeConverter
    fun infoToJson(info: Info): String = Gson().toJson(info)

    @TypeConverter
    fun infoFromJson(json: String): Info = Gson().fromJson(json, Info::class.java)

    @TypeConverter
    fun instructionToJson(instruction: Instructions): String = Gson().toJson(instruction)

    @TypeConverter
    fun instructionFromJson(json: String): Instructions = Gson().fromJson(json, Instructions::class.java)

    @TypeConverter
    fun instructionItemToJson(instructionItem: InstructionItem): String = Gson().toJson(instructionItem)

    @TypeConverter
    fun instructionItemFromJson(json: String): InstructionItem = Gson().fromJson(json, InstructionItem::class.java)

    @TypeConverter
    fun sectionToJson(section: Section): String = Gson().toJson(section)

    @TypeConverter
    fun sectionFromJson(json: String): Section = Gson().fromJson(json, Section::class.java)

    @TypeConverter
    fun stepToJson(step: Step): String = Gson().toJson(step)

    @TypeConverter
    fun stepFromJson(json: String): Step = Gson().fromJson(json, Step::class.java)
}