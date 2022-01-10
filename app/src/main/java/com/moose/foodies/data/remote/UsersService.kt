package com.moose.foodies.data.remote

import com.moose.foodies.FoodiesApplication
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.RawRecipe
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.util.BASE_URL
import dagger.hilt.android.EntryPointAccessors
import io.ktor.client.*
import io.ktor.client.request.*

object UsersService {

    private var client: HttpClient

    init {
        val context = FoodiesApplication.appContext
        val entryPoint = EntryPointAccessors.fromApplication(context, ClientHelper::class.java)
        client = entryPoint.client()
    }

    suspend fun getProfile(id: String): Profile{
        val url = "$BASE_URL/api/users/profile/$id"
        return client.get(url)
    }

    suspend fun discoverUsers(): List<Profile>{
        val url = "$BASE_URL/api/users/discover"
        return client.get(url)
    }

    suspend fun updateProfile(update: Profile): Profile{
        val url = "$BASE_URL/api/users/update"
        return client.put{url(url); body = update}
    }
}