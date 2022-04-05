package com.moose.foodies.domain.repositories

import com.moose.foodies.data.local.RecipesDao
import com.moose.foodies.data.remote.RecipesService
import com.moose.foodies.domain.models.Item
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.data.models.SearchData
import com.moose.foodies.domain.models.CompleteRecipe
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.util.Preferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RecipeRepository {
    fun getProfile(): Flow<Profile>

    suspend fun getRecipe(id: String): CompleteRecipe

    suspend fun updateProfile(profile: Profile)
}
