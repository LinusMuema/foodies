package com.moose.foodies.domain.usecases

import com.moose.foodies.domain.models.CompleteRecipe
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.repositories.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeUseCases @Inject constructor(private val recipeRepository: RecipeRepository) {
    fun getProfile(): Flow<Profile> {
        return recipeRepository.getProfile()
    }

    suspend fun getRecipe(id: String): CompleteRecipe {
        return recipeRepository.getRecipe(id)
    }

    suspend fun updateProfile(profile: Profile) {
        recipeRepository.updateProfile(profile)
    }

}