package com.moose.foodies.features.feature_auth.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.moose.foodies.di.factory.DaggerWorkerFactory.ChildWorkerFactory
import com.moose.foodies.features.feature_auth.data.AuthRepository
import javax.inject.Inject

class UpdateWorker(
    private val repository: AuthRepository,
    params: WorkerParameters,
   val context: Context
): Worker(context, params) {

    override fun doWork(): Result {
        return repository.getBackedUpFavorites()
            .flatMap {
                repository.updateFavorites(it)
                    .toSingleDefault(Result.success())
                    .onErrorReturnItem(Result.failure())
            }.blockingGet()
    }

    class Factory @Inject constructor( private val repository: AuthRepository): ChildWorkerFactory {

        override fun create(context: Context, parameters: WorkerParameters): ListenableWorker {
            return UpdateWorker(repository, parameters, context)
        }

    }
}