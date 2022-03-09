package com.moose.foodies.data.remote

import com.moose.foodies.domain.models.*
import com.moose.foodies.util.BASE_URL
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class RecipesService @Inject constructor(val client: HttpClient) {

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
        return try {
            client.get(url)
        }  catch (e: NoTransformationFoundException){
            val recipes: String = client.get(url)
            val json = Json { ignoreUnknownKeys = true }
            json.decodeFromString(recipes)
        }
    }

    suspend fun uploadRecipe(recipe: RawRecipe): Recipe{
        val url = "$BASE_URL/api/recipes"
        return client.post{url(url); body = recipe}
    }

    suspend fun searchRecipe(search: SearchData): List<Recipe> {
        val url = "$BASE_URL/api/recipes/search"
        return client.post { url(url); body = search }
    }
}