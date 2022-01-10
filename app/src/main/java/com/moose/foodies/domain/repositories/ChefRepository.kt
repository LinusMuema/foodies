package com.moose.foodies.domain.repositories

import com.moose.foodies.data.local.UserDao
import com.moose.foodies.data.remote.RecipesService
import com.moose.foodies.data.remote.UsersService
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.util.Preferences
import javax.inject.Inject

interface ChefRepository {

    suspend fun getChef(id: String): Profile

    suspend fun getChefRecipes(id: String): List<Recipe>
}

class ChefRepositoryImpl @Inject constructor(val userDao: UserDao, val preferences: Preferences): ChefRepository {

    override suspend fun getChef(id: String): Profile {
        return userDao.getChef(id) ?: UsersService.getProfile(id)
    }

    override suspend fun getChefRecipes(id: String): List<Recipe> {
        return RecipesService.getUserRecipes(id)
    }
}