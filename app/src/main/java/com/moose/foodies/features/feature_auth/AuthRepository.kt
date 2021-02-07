package com.moose.foodies.features.feature_auth

import com.moose.foodies.network.ApiEndpoints
import io.reactivex.Single
import javax.inject.Inject

interface AuthRepository {
    fun register(email: Credential): Single<TokenResponse>
}

class AuthRepositoryImpl @Inject constructor(private val apiEndpoints: ApiEndpoints): AuthRepository {

    override fun register(email: Credential): Single<TokenResponse> = apiEndpoints.register(email)
}