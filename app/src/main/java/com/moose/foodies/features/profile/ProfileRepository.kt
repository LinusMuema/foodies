package com.moose.foodies.features.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import com.moose.foodies.models.Profile
import com.moose.foodies.models.Recipe
import com.moose.foodies.util.UploadState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    val profile: Flow<Profile>

    val recipes: Flow<List<Recipe>>

    val progress: LiveData<UploadState>

    fun logout()

    fun clearProgress(): UploadState.Idle

    suspend fun updateProfile(profile: Profile)

    suspend fun uploadImage(dir: String, path: Uri)

}

@Module
@InstallIn(ViewModelComponent::class)
abstract class ProfileRepositoryBinding{

    @Binds
    abstract fun provideRepository(impl: ProfileRepositoryImpl): ProfileRepository
}