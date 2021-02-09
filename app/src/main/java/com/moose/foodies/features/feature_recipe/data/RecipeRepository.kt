package com.moose.foodies.features.feature_recipe.data

import com.moose.foodies.features.feature_home.domain.Recipe
import com.moose.foodies.features.feature_recipe.domain.toPresentation
import com.moose.foodies.local.FoodiesDao
import com.moose.foodies.remote.ApiEndpoints
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface RecipeRepository {

    fun getLocalRecipes(id: Int): Single<Recipe>

    fun getRemoteRecipe(id: Int): Single<Recipe>

    fun checkFavorite(id: Int): Single<Recipe>

    fun addFavorite(recipe: Recipe): Completable

    fun deleteFavorite(id: Int): Completable
}

class RecipeRepositoryImpl @Inject constructor(
    private val dao: FoodiesDao,
    private val api: ApiEndpoints
): RecipeRepository{

    override fun getLocalRecipes(id: Int): Single<Recipe> = dao.getHomeData().toPresentation(id)

    override fun getRemoteRecipe(id: Int): Single<Recipe> = api.getRecipeById(id)

    override fun checkFavorite(id: Int): Single<Recipe> = dao.getFavoriteById(id)

    override fun addFavorite(recipe: Recipe): Completable = dao.insertFavorite(recipe)

    override fun deleteFavorite(id: Int): Completable = dao.deleteFavorite(id)

}