package com.moose.foodies.features.feature_home

import androidx.lifecycle.ViewModel
import com.moose.foodies.di.ActivityScope
import com.moose.foodies.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideHome(): HomeActivity

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    abstract fun provideHomeRepository(impl: HomeRepositoryImpl): HomeRepository
}