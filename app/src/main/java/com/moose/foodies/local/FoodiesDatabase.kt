package com.moose.foodies.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moose.foodies.features.feature_home.HomeData
import com.moose.foodies.features.feature_home.Recipe

@Database(entities = [HomeData::class, Recipe::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FoodiesDatabase: RoomDatabase() {
    abstract fun getDao(): FoodiesDao

    companion object{
        const val DB_NAME="foodies"
    }
}