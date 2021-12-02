package com.moose.foodies.domain.repositories

import android.net.Uri
import androidx.lifecycle.LiveData
import com.moose.foodies.data.local.ItemsDao
import com.moose.foodies.data.local.UserDao
import com.moose.foodies.data.remote.ApiEndpoints
import com.moose.foodies.domain.models.Item
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.RawRecipe
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.util.Cloudinary
import com.moose.foodies.util.UploadState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AddRepository {

    val profile: Flow<Profile>

    val progress: LiveData<UploadState>

    suspend fun uploadImage(dir: String, path: Uri)

    suspend fun getItems(name: String,  type: String): List<Item>

    suspend fun uploadRecipe(recipe: RawRecipe): Recipe
}

class AddRepositoryImpl @Inject constructor(val dao: ItemsDao, val userDao: UserDao, val api: ApiEndpoints, val cloudinary: Cloudinary): AddRepository {

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
        dao.addRecipe(result.copy(type = "PERSONAL"))
        return result
    }
}