package com.moose.foodies.domain.repositories

import com.moose.foodies.data.remote.ApiEndpoints
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.util.Preferences
import dagger.Module
import javax.inject.Inject

interface ChefRepository {
    suspend fun getPrefsChef(): Profile
}

class ChefRepositoryImpl @Inject constructor(val api: ApiEndpoints, val preferences: Preferences): ChefRepository {

    override suspend fun getPrefsChef(): Profile {
        return preferences.getChef()
    }

}