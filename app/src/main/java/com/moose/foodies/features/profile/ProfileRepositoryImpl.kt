package com.moose.foodies.features.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import com.moose.foodies.local.ItemsDao
import com.moose.foodies.local.UserDao
import com.moose.foodies.models.Profile
import com.moose.foodies.models.Recipe
import com.moose.foodies.remote.ApiEndpoints
import com.moose.foodies.util.Cloudinary
import com.moose.foodies.util.UploadState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dao: UserDao,
    private val itemsDao: ItemsDao,
    private val api: ApiEndpoints,
    private val cloudinary: Cloudinary
): ProfileRepository {

    override val profile: Flow<Profile>
        get() = dao.getProfile()

    override val recipes: Flow<List<Recipe>>
        get() = itemsDao.getUserRecipes()

    override val progress: LiveData<UploadState>
        get() = cloudinary.progress

    override fun clearProgress() = cloudinary.clearProgress()

    override suspend fun updateProfile(profile: Profile) {
        dao.addProfile(api.updateProfile(profile))
    }

    override suspend fun uploadImage(dir: String, path: Uri) {
        cloudinary.uploadImage(dir, path)
    }
}