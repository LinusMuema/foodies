package com.moose.foodies.features.feature_search

import androidx.lifecycle.ViewModel
import com.moose.foodies.di.ActivityScope
import com.moose.foodies.di.ViewModelKey
import com.moose.foodies.features.feature_search.data.SearchRepository
import com.moose.foodies.features.feature_search.data.SearchRepositoryImpl
import com.moose.foodies.features.feature_search.presentation.RecipesFragment
import com.moose.foodies.features.feature_search.presentation.SearchActivity
import com.moose.foodies.features.feature_search.presentation.SearchViewModel
import com.moose.foodies.features.feature_search.presentation.VideosFragment
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

    @Binds
    abstract fun provideSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @ContributesAndroidInjector
    abstract fun provideVideos(): VideosFragment

    @ContributesAndroidInjector
    abstract fun provideRecipes(): RecipesFragment

}