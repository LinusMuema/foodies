package com.moose.foodies.backup

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.moose.foodies.local.DbRepository
import com.moose.foodies.remote.ApiRepository
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class FavoritesBackupWorker(context: Context, params: WorkerParameters): Worker(context, params) {

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
        return Result.success()
    }
}