package com.moose.foodies.domain.usecases

import com.moose.foodies.domain.models.CompleteRecipe
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.domain.repositories.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FeedUseCases @Inject constructor(private val feedRepository: FeedRepository) {

    suspend fun refreshData(): String {
        return feedRepository.refreshData()
    }

    suspend fun getChefs(): List<Profile> {
        return feedRepository.getChefs()
    }

    fun getUserProfile(): Flow<Profile> {
        return feedRepository.getProfile()
    }

    suspend fun getFeaturedRecipes(): List<CompleteRecipe> {
        return feedRepository.getFeaturedRecipes()
    }

    suspend fun getRecipesByCategory(category: List<String>): List<CompleteRecipe> {
        return feedRepository.getRecipesByCategory(category)
    }

}