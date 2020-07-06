package com.moose.foodies.di.modules

import com.moose.foodies.features.search.RecipesFragment
import com.moose.foodies.features.search.VideosFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract fun provideVideos(): VideosFragment

    @ContributesAndroidInjector
    abstract fun provideRecipes(): RecipesFragment
}