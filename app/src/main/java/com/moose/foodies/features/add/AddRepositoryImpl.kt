package com.moose.foodies.features.add

import android.net.Uri
import androidx.lifecycle.LiveData
import com.moose.foodies.local.ItemsDao
import com.moose.foodies.local.UserDao
import com.moose.foodies.models.Item
import com.moose.foodies.models.Profile
import com.moose.foodies.models.RawRecipe
import com.moose.foodies.models.Recipe
import com.moose.foodies.remote.ApiEndpoints
import com.moose.foodies.util.Cloudinary
import com.moose.foodies.util.UploadState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddRepositoryImpl @Inject constructor(
    private val dao: ItemsDao,
    private val userDao: UserDao,
    private val api: ApiEndpoints,
    private val cloudinary: Cloudinary,
): AddRepository {

    override val profile: Flow<Profile>
        get() = userDao.getProfile()

    override val progress: LiveData<UploadState>
        get() = cloudinary.progress

    override suspend fun uploadImage(dir: String, path: Uri) = cloudinary.uploadImage(dir, path)

    override suspend fun getItems(name: String, type: String): List<Item> {
        return dao.searchItem("%$name%", type)
    }

    override suspend fun uploadRecipe(recipe: RawRecipe): Recipe {
        cloudinary.clearProgress()
        val result = api.uploadRecipe(recipe)
        dao.addRecipe(result)
        return result
    }
}