package com.moose.foodies.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.moose.foodies.data.local.RecipesDao
import com.moose.foodies.data.local.UserDao
import com.moose.foodies.data.remote.RecipesService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class RecipesWorker @AssistedInject constructor(
    private val userDao: UserDao,
    private val recipesDao: RecipesDao,
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val recipesService: RecipesService,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            val id = userDao.getProfile().first().id
            val recipes = recipesService.getUserRecipes(id)
            recipesDao.addRecipe(*recipes.map { it.type = "PERSONAL"; it }.toTypedArray())
            Result.success(workDataOf("status" to "success"))
        } catch (e: Throwable) { Result.failure(workDataOf("error" to e.message)) }
    }
}