package com.moose.foodies.features.feature_favorites.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.moose.foodies.di.factory.DaggerWorkerFactory.ChildWorkerFactory
import com.moose.foodies.features.feature_favorites.data.FavoritesRepository
import com.moose.foodies.features.feature_favorites.domain.Backup
import com.moose.foodies.util.PreferenceHelper
import javax.inject.Inject

class BackupWorker(
    private val repository: FavoritesRepository,
    params: WorkerParameters,
    val context: Context
): Worker(context, params) {

    override fun doWork(): Result {
        PreferenceHelper.setBackupStatus(context, false)
        return repository.getFavorites()
            .flatMap { repository.backupFavorites(Backup(it)).retry(3) }
            .map {
                if (it.status == "success") Result.success()
                else Result.failure()
            }.blockingGet()
    }

    class Factory @Inject constructor(private val repository: FavoritesRepository): ChildWorkerFactory {

        override fun create(context: Context, parameters: WorkerParameters): ListenableWorker {
            return BackupWorker(repository, parameters, context)
        }

    }
}