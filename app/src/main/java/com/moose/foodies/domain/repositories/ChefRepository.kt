package com.moose.foodies.domain.repositories

import com.moose.foodies.data.local.UserDao
import com.moose.foodies.data.remote.ApiEndpoints
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.util.Preferences
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface ChefRepository {
    suspend fun getPrefsChef(): Profile

    suspend fun fetchNetChef(id: String): Profile

    suspend fun getChefRecipes(id: String): List<Recipe>
}

class ChefRepositoryImpl @Inject constructor(val userDao: UserDao, val api: ApiEndpoints, val preferences: Preferences): ChefRepository {

    override suspend fun getPrefsChef(): Profile {
        return preferences.getChef()
    }

    override suspend fun fetchNetChef(id: String): Profile {
        return userDao.getChef(id) ?: api.getChef(id)
    }

    override suspend fun getChefRecipes(id: String): List<Recipe> {
        return api.getUserRecipes(id)
    }

}