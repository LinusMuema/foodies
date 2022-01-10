package com.moose.foodies.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.moose.foodies.data.local.ItemsDao
import com.moose.foodies.data.remote.RecipesService
import com.moose.foodies.util.Preferences
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.text.SimpleDateFormat
import java.util.*

@HiltWorker
class ItemWorker @AssistedInject constructor(
    private val dao: ItemsDao,
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val preferences: Preferences,
): CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            val update = preferences.getUpdate()
            val time = SimpleDateFormat("hh:mm:ss", Locale.UK).format(Date())
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())

            dao.addItems(RecipesService.getItems(update))
            preferences.setUpdate("${date}T$time")

            Result.success(workDataOf("status" to "success"))
        } catch (e: Throwable) { Result.failure(workDataOf("error" to e.message)) }
    }
}