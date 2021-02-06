package com.moose.foodies.network

import com.moose.foodies.models.Intolerances
import com.moose.foodies.models.Recipes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ApiRepository @Inject constructor(private val apiEndpoints: ApiEndpoints) {

    open fun getIntolerances() = apiEndpoints.getAllIntolerances()

    open fun updateIntolerances( intolerance: Intolerances) = apiEndpoints.updateIntolerances(intolerance)

    open fun getRecipes() = apiEndpoints.getRecipes()

    open fun searchRecipes(name: String) = apiEndpoints.searchRecipe(name)

    open fun searchFridgeRecipes(ingredients: String) = apiEndpoints.searchFridgeRecipes(ingredients)

    open fun getRecipeById(id: String) = apiEndpoints.getRecipeById(id)

    open fun backupFavorites(recipes: Recipes) = apiEndpoints.backupRecipes(recipes)

    open fun getBackedUpRecipes() = apiEndpoints.getBackedUpRecipes()

}