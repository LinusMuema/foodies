package com.moose.foodies.backup

import android.content.Context
import android.util.Log
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.moose.foodies.db.DbRepository
import com.moose.foodies.di.WorkerFactory
import com.moose.foodies.di.network.ApiRepository
import com.moose.foodies.models.Recipes
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Single


class FavoritesBackupWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val apiRepository: ApiRepository,
    private val dbRepository: DbRepository
): RxWorker(context, params) {

    override fun createWork(): Single<Result> {
        Log.d("Backup", "createWork: Started work")g
        return dbRepository.getFavorites().flatMap {
            Log.d("Backup", "createWork: From db : $it")
            return@flatMap apiRepository.backupFavorites(Recipes(0, "", it, "", ""))
        }.map {
            Log.d("Backup", "createWork: From api $it")
            if (it.message == "success") return@map Result.success()
            else return@map Result.failure()
        }.firstOrError()
    }

    @AssistedInject.Factory
    interface Factory : WorkerFactory

}