package com.moose.foodies.features.feature_ingredients.data

import com.moose.foodies.features.feature_ingredients.domain.Recipe
import com.moose.foodies.remote.ApiEndpoints
import io.reactivex.Single
import javax.inject.Inject

interface IngredientsRepository {

    fun getRecipes(ingredients: String): Single<List<Recipe>>
}

class IngredientsRepositoryImpl @Inject constructor(
    private val api: ApiEndpoints
): IngredientsRepository {

    override fun getRecipes(ingredients: String): Single<List<Recipe>> {
        return api.getRecipesByIngredients(ingredients)
    }

}