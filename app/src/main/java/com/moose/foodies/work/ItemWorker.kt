package com.moose.foodies.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.moose.foodies.local.ItemsDao
import com.moose.foodies.remote.ApiEndpoints
import com.moose.foodies.util.Preferences
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.text.SimpleDateFormat
import java.util.*

@HiltWorker
class ItemWorker @AssistedInject constructor(
    private val dao: ItemsDao,
    private val api: ApiEndpoints,
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val preferences: Preferences,
): CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        // 1. get the last update timestamp from preferences
        val update = preferences.getUpdate()

        // 2. Get the items from network
        val items = api.getItems(update)

        // 3. Add items to local db
        dao.addItems(items)

        // 4. update our "update" record in preferences
        val now = SimpleDateFormat("yyyy:MM:dd_HH:mm:ss", Locale.US).format(Date())
        preferences.setUpdate(now)

        return Result.success()
    }
}