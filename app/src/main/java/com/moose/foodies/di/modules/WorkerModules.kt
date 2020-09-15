package com.moose.foodies.di.modules

import com.moose.foodies.backup.FavoritesBackupWorker
import com.moose.foodies.di.WorkerFactory
import com.moose.foodies.di.WorkerKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WorkerModules {

    @Binds
    @IntoMap
    @WorkerKey(FavoritesBackupWorker::class)
    abstract fun bindFavoritesBackupWorker(factory: FavoritesBackupWorker.Factory): WorkerFactory
}