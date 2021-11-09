package com.moose.foodies.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.moose.foodies.local.ItemsDao
import com.moose.foodies.local.UserDao
import com.moose.foodies.remote.ApiEndpoints
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class RecipesWorker @AssistedInject constructor(
    private val userDao: UserDao,
    private val api: ApiEndpoints,
    private val itemsDao: ItemsDao,
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            val id = userDao.getProfile().first()._id
            val recipes = api.getUserRecipes(id)
            itemsDao.addRecipe(*recipes.map { it.type = "PERSONAL"; it }.toTypedArray())
            Result.success()
        } catch (e: Throwable) {
            Log.e("ItemWorker", "doWork: $e")
            Result.failure()
        }
    }
}