package com.moose.foodies.backup

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.moose.foodies.db.DbRepository
import com.moose.foodies.di.network.ApiRepository
import com.moose.foodies.models.Recipes
import io.reactivex.Single
import javax.inject.Inject


class FavoritesBackupWorker @Inject constructor(
    context: Context,
    params: WorkerParameters,
    private val apiRepository: ApiRepository,
    private val dbRepository: DbRepository
): RxWorker(context, params) {
    override fun createWork(): Single<Result> {
        return dbRepository.getFavorites()
            .flatMap {
                apiRepository.backupFavorites(Recipes(0,"", it, null, null))
                    .map { Result.success()}
            }
            .toList()
            .map { Result.success() }
    }

}