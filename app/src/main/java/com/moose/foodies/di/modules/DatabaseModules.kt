package com.moose.foodies.di.modules

import android.content.Context
import androidx.room.Room
import com.moose.foodies.FoodiesApplication
import com.moose.foodies.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModules {

    @Singleton
    @Provides
    fun provideDB(context: FoodiesApplication) = Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME)
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    fun provideDao(appDatabase: AppDatabase) = appDatabase.getDao()
}