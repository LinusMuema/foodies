package com.moose.foodies.features.search

import androidx.lifecycle.ViewModel
import com.moose.foodies.di.ActivityScope
import com.moose.foodies.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SearchModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideSearch(): SearchActivity

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun provideVideos(): VideosFragment

    @ContributesAndroidInjector
    abstract fun provideRecipes(): RecipesFragment

}