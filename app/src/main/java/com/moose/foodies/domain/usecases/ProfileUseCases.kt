package com.moose.foodies.domain.usecases

import android.net.Uri
import androidx.lifecycle.LiveData
import com.moose.foodies.data.remote.UploadState
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileUseCases @Inject constructor(private val profileRepository: ProfileRepository) {

    fun clearProgress() = profileRepository.clearProgress()

    fun getProfile(): Flow<Profile> = profileRepository.getProfile()

    fun getProgress(): LiveData<UploadState> = profileRepository.getProgress()

    suspend fun getFavorites(ids: List<String>): List<Recipe> {
        return profileRepository.getFavorites(ids)
    }

    suspend fun getRecipes(id: String): List<Recipe>{
        return profileRepository.getRecipes(id)
    }

    suspend fun updateProfile(update: Profile){
        return profileRepository.updateProfile(update)
    }

    suspend fun uploadImage(directory: String, path: Uri){
        return profileRepository.uploadImage(directory, path)
    }

    suspend fun clearData() {
        profileRepository.clearData()
    }

}