package com.moose.foodies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moose.foodies.models.Recipe
import com.moose.foodies.models.Recipes

@Database(entities = [Recipes::class, Recipe::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getDao(): FoodiesDao

    companion object{
        const val DB_NAME="foodies"
    }
}