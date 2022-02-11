package com.moose.foodies.domain.repositories

import android.net.Uri
import androidx.lifecycle.LiveData
import com.moose.foodies.data.local.RecipesDao
import com.moose.foodies.data.local.UserDao
import com.moose.foodies.data.remote.UsersService
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.util.Cloudinary
import com.moose.foodies.util.Preferences
import com.moose.foodies.util.UploadState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ProfileRepository {
    val profile: Flow<Profile>

    val recipes: Flow<List<Recipe>>

    val progress: LiveData<UploadState>

    suspend fun clearData()

    fun clearProgress(): UploadState.Idle

    suspend fun updateProfile(profile: Profile)

    suspend fun uploadImage(dir: String, path: Uri)
}

class ProfileRepositoryImpl @Inject constructor(
    val userDao: UserDao,
    val recipesDao: RecipesDao,
    val cloudinary: Cloudinary,
    val preferences: Preferences,
    val usersService: UsersService,
): ProfileRepository {

    override val profile: Flow<Profile>
        get() = userDao.getProfile()

    override val recipes: Flow<List<Recipe>>
        get() = recipesDao.getUserRecipes()

    override val progress: LiveData<UploadState>
        get() = cloudinary.progress

    override fun clearProgress() = cloudinary.clearProgress()

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