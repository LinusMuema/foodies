package com.moose.foodies.features.add

import android.net.Uri
import androidx.lifecycle.LiveData
import com.moose.foodies.models.Item
import com.moose.foodies.models.Profile
import com.moose.foodies.models.RawRecipe
import com.moose.foodies.models.Recipe
import com.moose.foodies.util.UploadState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

interface AddRepository {

    val profile: Flow<Profile>

    val progress: LiveData<UploadState>

    suspend fun uploadImage(dir: String, path: Uri)

    suspend fun getItems(name: String,  type: String): List<Item>

    suspend fun uploadRecipe(recipe: RawRecipe): Recipe
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class AddRepositoryBinding {

    @Binds
    abstract fun provideAddRepository(impl: AddRepositoryImpl): AddRepository
}