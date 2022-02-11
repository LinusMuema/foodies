package com.moose.foodies.domain.repositories

import com.moose.foodies.data.local.UserDao
import com.moose.foodies.data.remote.RecipesService
import com.moose.foodies.data.remote.UsersService
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import javax.inject.Inject

interface ChefRepository {

    suspend fun getChef(id: String): Profile

    suspend fun getChefRecipes(id: String): List<Recipe>
}

class ChefRepositoryImpl @Inject constructor(
    val userDao: UserDao,
    val usersService: UsersService,
    val recipesService: RecipesService,
): ChefRepository {

    override suspend fun getChef(id: String): Profile {
        return userDao.getChef(id) ?: usersService.getProfile(id)
    }

    override suspend fun getChefRecipes(id: String): List<Recipe> {
        return recipesService.getUserRecipes(id)
    }
}