package com.moose.foodies.di.modules

import com.moose.foodies.backup.FavoritesBackupWorker
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface WorkerModules {

    @ContributesAndroidInjector
    fun provideFavoritesBackupWorker(): FavoritesBackupWorker
}