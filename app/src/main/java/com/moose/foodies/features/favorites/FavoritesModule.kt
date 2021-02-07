package com.moose.foodies.features.favorites

import androidx.lifecycle.ViewModel
import com.moose.foodies.backup.FavoritesBackupWorker
import com.moose.foodies.di.ActivityScope
import com.moose.foodies.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FavoritesModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideFavorites(): FavoritesActivity

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindFavoritesViewModel(favoritesViewModel: FavoritesViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun provideFavoritesBackupWorker(): FavoritesBackupWorker
}