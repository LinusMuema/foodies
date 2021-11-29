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

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit  var appContext: Context
    }
}