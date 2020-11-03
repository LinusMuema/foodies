package com.moose.foodies.backup

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.moose.foodies.db.DbRepository
import com.moose.foodies.di.network.ApiRepository
import com.moose.foodies.models.Recipes
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class FavoritesBackupWorker( context: Context, params: WorkerParameters): Worker(context, params) {

    object ContextInjection {
        @JvmStatic
        fun inject(to: Any, with: Context) {
            (with.applicationContext as HasAndroidInjector).androidInjector().inject(to)
        }
    }

    init {
        ContextInjection.inject(to = this, with = context)
    }

    @Inject lateinit var apiRepository: ApiRepository
    @Inject lateinit var dbRepository: DbRepository

    override fun doWork(): Result {
        return dbRepository.getFavorites()
            .flatMap { apiRepository.backupFavorites(Recipes(0, "", it, "", "")) }
            .map {
            if (it.message == "success") Result.success()
            else Result.failure()
        }.blockingFirst()
    }
}