package com.moose.foodies.domain.repositories

import com.moose.foodies.domain.models.CompleteRecipe
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    fun getProfile(): Flow<Profile>

    suspend fun refreshData(): String

    suspend fun getChefs(): List<Profile>

    suspend fun getFeaturedRecipes(): List<CompleteRecipe>

    suspend fun getRecipesByCategory(categories: List<String>): List<CompleteRecipe>
}

