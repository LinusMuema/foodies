package com.moose.foodies

import android.app.Application
import android.content.Context
import androidx.core.view.WindowCompat
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.moose.foodies.work.ItemWorker
import com.moose.foodies.work.RecipesWorker
import dagger.hilt.android.HiltAndroidApp
import io.sentry.Sentry
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
open class FoodiesApplication: Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    lateinit var manager: WorkManager

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        manager = WorkManager.getInstance(this)

        itemWork()
    }

    companion object {
        lateinit  var appContext: Context
    }

    // start item work
    private fun itemWork(){
        val work = PeriodicWorkRequest
            .Builder(ItemWorker::class.java, 6, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        manager.enqueue(work)
    }
}