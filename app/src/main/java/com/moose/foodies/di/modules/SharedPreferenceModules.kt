package com.moose.foodies.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class SharedPreferenceModules {

    @Provides
    fun provideSharedPreference(context: Context): SharedPreferences = context.getSharedPreferences("moose.foodies", Context.MODE_PRIVATE)
}