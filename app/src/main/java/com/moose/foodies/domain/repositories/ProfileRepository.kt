package com.moose.foodies.domain.repositories

import android.net.Uri
import androidx.lifecycle.LiveData
import com.moose.foodies.data.remote.UploadState
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun clearData()

    fun clearProgress()

    fun getProfile(): Flow<Profile>

    fun getProgress(): LiveData<UploadState>

    suspend fun updateProfile(profile: Profile)

    suspend fun uploadImage(dir: String, path: Uri)

    suspend fun getRecipes(id: String): List<Recipe>

    suspend fun getFavorites(ids: List<String>): List<Recipe>
}