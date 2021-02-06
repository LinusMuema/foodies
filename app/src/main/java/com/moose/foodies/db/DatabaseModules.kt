package com.moose.foodies.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModules {

    @Singleton
    @Provides
    fun provideDB(context: Context): FoodiesDatabase {
        return Room.databaseBuilder(context, FoodiesDatabase::class.java, FoodiesDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(appDatabase: FoodiesDatabase) = appDatabase.getDao()
}