package com.moose.foodies.backup

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.moose.foodies.di.network.ApiRepository
import com.moose.foodies.models.Recipes
import io.reactivex.Single
import javax.inject.Inject

class FavoritesBackupWorker(context: Context, params: WorkerParameters): RxWorker(context, params) {
    @Inject lateinit var apiRepository: ApiRepository

    override fun createWork(): Single<Result> {
        val recipes = Gson().fromJson(inputData.getString("FAVORITES_BACKUP"), Recipes::class.java)
        return apiRepository.backupFavorites(recipes)
            .map { Result.success() }
            .onErrorReturn { Result.failure() }

    }

}