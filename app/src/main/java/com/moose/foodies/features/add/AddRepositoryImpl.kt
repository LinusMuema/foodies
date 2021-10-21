package com.moose.foodies.features.add

import android.net.Uri
import androidx.lifecycle.LiveData
import com.moose.foodies.local.ItemsDao
import com.moose.foodies.models.Item
import com.moose.foodies.models.RawRecipe
import com.moose.foodies.models.Recipe
import com.moose.foodies.remote.ApiEndpoints
import com.moose.foodies.util.Cloudinary
import com.moose.foodies.util.UploadState
import javax.inject.Inject

class AddRepositoryImpl @Inject constructor(
    private val dao: ItemsDao,
    private val api: ApiEndpoints,
    private val cloudinary: Cloudinary,
): AddRepository {

    override val progress: LiveData<UploadState>
        get() = cloudinary.progress

    override suspend fun uploadImage(path: Uri) = cloudinary.uploadImage(path)

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