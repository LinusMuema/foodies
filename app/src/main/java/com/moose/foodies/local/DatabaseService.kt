package com.moose.foodies.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseService {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): FoodiesDatabase {
        return Room.databaseBuilder(context, FoodiesDatabase::class.java, FoodiesDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: FoodiesDatabase) = appDatabase.getUserDao()
}