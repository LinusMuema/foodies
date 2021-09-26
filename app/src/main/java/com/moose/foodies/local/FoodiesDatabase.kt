package com.moose.foodies.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moose.foodies.models.Profile
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@TypeConverters(Converters::class)
@Database(entities = [Profile::class], version = 1, exportSchema = false)
abstract class FoodiesDatabase: RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object{
        const val DB_NAME="foodies"
    }
}