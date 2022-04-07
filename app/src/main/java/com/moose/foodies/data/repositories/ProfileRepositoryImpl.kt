package com.moose.foodies.data.repositories

import android.net.Uri
import androidx.lifecycle.LiveData
import com.moose.foodies.data.local.RecipesDao
import com.moose.foodies.data.local.UserDao
import com.moose.foodies.data.remote.Cloudinary
import com.moose.foodies.data.remote.RecipesService
import com.moose.foodies.data.remote.UploadState
import com.moose.foodies.data.remote.UsersService
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.domain.repositories.ProfileRepository
import com.moose.foodies.util.Preferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val recipesDao: RecipesDao,
    private val cloudinary: Cloudinary,
    private val preferences: Preferences,
    private val usersService: UsersService,
    private val recipesService: RecipesService,
): ProfileRepository {

    override fun clearProgress() {
        cloudinary.clearProgress()
    }

    override fun getProfile(): Flow<Profile> {
        return userDao.getProfile()
    }

    override suspend fun getRecipes(id: String): List<Recipe> {
        return recipesDao.getUserRecipes(id)
    }

    override suspend fun getFavorites(ids: List<String>): List<Recipe> {
        return ids.map {
            recipesDao.getRecipeById(it) ?: recipesService.getRecipe(it)
        }
    }

    override fun getProgress(): LiveData<UploadState> {
        return cloudinary.progress
    }

    override suspend fun clearData() {
        preferences.setToken(null)
        preferences.setUpdate(null)

        userDao.nukeProfile()
        recipesDao.nukeRecipes()
    }

    override suspend fun updateProfile(profile: Profile) {
        val update = usersService.updateProfile(profile)
        userDao.addProfile(update.copy(current = true))
    }

    override suspend fun uploadImage(dir: String, path: Uri) {
        cloudinary.uploadImage(dir, path)
    }
}