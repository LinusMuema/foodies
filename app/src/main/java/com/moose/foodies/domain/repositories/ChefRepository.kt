package com.moose.foodies.domain.repositories

import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe

interface ChefRepository {

    suspend fun getChef(id: String): Profile

    suspend fun getRemoteRecipes(id: String): List<Recipe>
}