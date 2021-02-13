package com.moose.foodies.features.feature_favorites.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.moose.foodies.di.factory.DaggerWorkerFactory.ChildWorkerFactory
import com.moose.foodies.features.feature_favorites.domain.Backup
import com.moose.foodies.local.FoodiesDao
import com.moose.foodies.remote.ApiEndpoints
import io.reactivex.Single
import javax.inject.Inject

class BackupWorker(
    private val api: ApiEndpoints,
    private val dao: FoodiesDao,
    params: WorkerParameters,
    context: Context
): RxWorker(context, params) {

    override fun createWork(): Single<Result> {
        return dao.getFavorites()
            .flatMap { api.backupRecipes(Backup(it)).retry(3) }
            .map {
                if (it.status == "success") Result.success()
                else Result.failure()
            }
    }

    class Factory @Inject constructor(
        private val api: ApiEndpoints,
        private val dao: FoodiesDao,
    ): ChildWorkerFactory {

        override fun create(context: Context, parameters: WorkerParameters): ListenableWorker {
            return BackupWorker(api, dao, parameters, context)
        }

    }
}