package com.moose.foodies.domain.repositories

import android.net.Uri
import androidx.lifecycle.LiveData
import com.moose.foodies.data.local.ItemsDao
import com.moose.foodies.data.local.UserDao
import com.moose.foodies.data.remote.ApiEndpoints
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.util.Cloudinary
import com.moose.foodies.util.Preferences
import com.moose.foodies.util.UploadState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
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

class ProfileRepositoryImpl @Inject constructor(val userDao: UserDao, val itemsDao: ItemsDao, val api: ApiEndpoints, val cloudinary: Cloudinary, val preferences: Preferences): ProfileRepository {

    override val profile: Flow<Profile>
        get() = userDao.getProfile()

    override val recipes: Flow<List<Recipe>>
        get() = itemsDao.getUserRecipes()

    override val progress: LiveData<UploadState>
        get() = cloudinary.progress

    override fun clearProgress() = cloudinary.clearProgress()

    override suspend fun clearData() {
        preferences.setToken(null)
        preferences.setUpdate(null)

        userDao.nukeProfile()
        itemsDao.nukeRecipes()
    }

    override suspend fun updateProfile(profile: Profile) {
        userDao.addProfile(api.updateProfile(profile))
    }

    override suspend fun uploadImage(dir: String, path: Uri) {
        cloudinary.uploadImage(dir, path)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class ProfileRepositoryBinding{

    @Binds
    abstract fun provideRepository(impl: ProfileRepositoryImpl): ProfileRepository
}