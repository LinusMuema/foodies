package com.moose.foodies.data.repositories

import com.moose.foodies.data.local.RecipesDao
import com.moose.foodies.data.local.UserDao
import com.moose.foodies.data.remote.RecipesService
import com.moose.foodies.data.remote.UsersService
import com.moose.foodies.domain.models.CompleteRecipe
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.domain.repositories.FeedRepository
import com.moose.foodies.util.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    val userDao: UserDao,
    val recipesDao: RecipesDao,
    val preferences: Preferences,
    val usersService: UsersService,
    val recipesService: RecipesService,
): FeedRepository {

    override suspend fun refreshData(): String {
        val chefs = usersService.getChefs()
        userDao.addProfile(*chefs.toTypedArray())

        val recipes = recipesService.getRandomRecipes()
        recipesDao.addRecipe(*recipes.toTypedArray())

        return "refresh done"
    }

    override suspend fun getChefs(): List<Profile> {
        val data = userDao.getChefs()
        return data.ifEmpty {
            val netChefs = usersService.getChefs()
            userDao.addProfile(*netChefs.toTypedArray())
            netChefs
        }
    }

    override fun getProfile(): Flow<Profile> {
        return userDao.getProfile()
    }

    override suspend fun getFeaturedRecipes(): List<Recipe> {
        val recipes = recipesDao.getRandomRecipes()
        return recipes.ifEmpty {
            val netRecipes = recipesService.getRandomRecipes()
            recipesDao.addRecipe(*netRecipes.toTypedArray())
            netRecipes
        }
    }

    override suspend fun getRecipesByCategory(categories: List<String>): List<Recipe> {
        val recipes = recipesDao.getRandomRecipes().filter {  hasCategories(it, categories) }
        return recipes.ifEmpty {
            val netRecipes = recipesService.getRandomRecipes()
            recipesDao.addRecipe(*netRecipes.toTypedArray())
            netRecipes.filter {  hasCategories(it, categories) }
        }
    }

    private fun hasCategories(recipe: Recipe, categories: List<String>): Boolean {
        return recipe.categories.any { categories.contains(it) }
    }

}