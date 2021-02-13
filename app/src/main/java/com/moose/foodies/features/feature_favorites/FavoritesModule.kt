package com.moose.foodies.features.feature_favorites

import androidx.lifecycle.ViewModel
import com.moose.foodies.di.ActivityScope
import com.moose.foodies.di.ViewModelKey
import com.moose.foodies.di.WorkerKey
import com.moose.foodies.di.factory.DaggerWorkerFactory.ChildWorkerFactory
import com.moose.foodies.features.feature_favorites.data.FavoritesRepository
import com.moose.foodies.features.feature_favorites.data.FavoritesRepositoryImpl
import com.moose.foodies.features.feature_favorites.presentation.FavoritesActivity
import com.moose.foodies.features.feature_favorites.presentation.FavoritesViewModel
import com.moose.foodies.features.feature_favorites.work.BackupWorker
import com.moose.foodies.features.feature_favorites.work.UpdateWorker
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

    @Binds
    @IntoMap
    @WorkerKey(BackupWorker::class)
    abstract fun provideBackupWorker(factory: BackupWorker.Factory): ChildWorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(UpdateWorker::class)
    abstract fun provideUpdateWorker(factory: UpdateWorker.Factory): ChildWorkerFactory

    @Binds
    abstract fun bindFavoritesRepository(impl: FavoritesRepositoryImpl): FavoritesRepository

}