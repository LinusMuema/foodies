package com.moose.foodies.domain.usecases

import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.domain.repositories.ChefRepository
import javax.inject.Inject

class ChefUseCases @Inject constructor(private val chefRepository: ChefRepository) {
    suspend fun getChef(id: String): Profile {
        return chefRepository.getChef(id)
    }

    suspend fun getRemoteRecipes(id: String): List<Recipe>{
        return chefRepository.getRemoteRecipes(id)
    }
}