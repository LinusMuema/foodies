package com.moose.foodies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moose.foodies.features.home.HomeData
import com.moose.foodies.features.home.Recipe

@Database(entities = [HomeData::class, Recipe::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FoodiesDatabase: RoomDatabase() {
    abstract fun getDao(): FoodiesDao

    companion object{
        const val DB_NAME="foodies"
    }
}