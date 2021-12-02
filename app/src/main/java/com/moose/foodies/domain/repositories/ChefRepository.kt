package com.moose.foodies.domain.repositories

import com.moose.foodies.data.remote.ApiEndpoints
import dagger.Module
import javax.inject.Inject

interface ChefRepository {
}

class ChefRepositoryImpl @Inject constructor(val api: ApiEndpoints): ChefRepository {

}