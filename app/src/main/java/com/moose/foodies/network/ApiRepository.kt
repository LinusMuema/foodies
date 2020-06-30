package com.moose.foodies.network

import com.moose.foodies.models.Credentials
import com.moose.foodies.models.Intolerance
import com.moose.foodies.models.Intolerances
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ApiRepository @Inject constructor(private val apiEndpoints: ApiEndpoints) {
    open fun login(credentials: Credentials) = apiEndpoints.login(credentials)

    open fun reset(email : String) = apiEndpoints.reset(email)

    open fun getIntolerances() = apiEndpoints.getAllIntolerances()

    open fun updateIntolerances( intolerance: Intolerances) = apiEndpoints.updateIntolerances(intolerance)

    open fun getRecipes() = apiEndpoints.getRecipes()

    open fun searchRecipes(name: String) = apiEndpoints.searchRecipe(name)

    open fun searchRecipeByIngredients(ingredients: String) = apiEndpoints.searchRecipeByIngredients(ingredients)

}