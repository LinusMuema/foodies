package com.moose.foodies.domain.usecases

import com.moose.foodies.domain.models.CompleteRecipe
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.domain.repositories.ChefRepository
import javax.inject.Inject

class ChefUseCases @Inject constructor(private val chefRepository: ChefRepository) {
    suspend fun getChef(id: String): Profile {
        TODO()
    }

    suspend fun getChefRecipes(id: String): List<Recipe>{
        TODO()
    }
}