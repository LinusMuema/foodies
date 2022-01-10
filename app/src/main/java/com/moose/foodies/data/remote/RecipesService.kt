package com.moose.foodies.data.remote

import com.moose.foodies.FoodiesApplication
import com.moose.foodies.domain.models.Item
import com.moose.foodies.domain.models.RawRecipe
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.util.BASE_URL
import dagger.hilt.android.EntryPointAccessors
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.get
import io.ktor.client.request.*

object RecipesService {
    private var client: HttpClient

    init {
        val context = FoodiesApplication.appContext
        val entryPoint = EntryPointAccessors.fromApplication(context, ClientHelper::class.java)
        client = entryPoint.client()
    }

    suspend fun getItems(update: String?): List<Item>{
        val url = "$BASE_URL/api/items"
        return client.get(url){ parameter("update", update)}
    }

    suspend fun getFeed(): List<Recipe> {
        val url = "$BASE_URL/api/recipes"
        return client.get(url)
    }

    suspend fun getRecipe(id: String): Recipe {
        val url = "$BASE_URL/api/recipes/$id"
        return client.get(url)
    }

    suspend fun getUserRecipes(id: String): List<Recipe>{
        val url = "$BASE_URL/api/recipes/user/$id"
        return client.get(url)
    }

    suspend fun uploadRecipe(recipe: RawRecipe): Recipe{
        val url = "$BASE_URL/api/recipes"
        return client.post{url(url); body = recipe}
    }
}