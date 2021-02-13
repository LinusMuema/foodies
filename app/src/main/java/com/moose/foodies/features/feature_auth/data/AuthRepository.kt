package com.moose.foodies.features.feature_auth.data

import com.moose.foodies.features.feature_auth.domain.Credential
import com.moose.foodies.features.feature_auth.domain.TokenResponse
import com.moose.foodies.remote.ApiEndpoints
import io.reactivex.Single
import javax.inject.Inject

interface AuthRepository {
    fun register(email: Credential): Single<TokenResponse>
}

class AuthRepositoryImpl @Inject constructor(private val apiEndpoints: ApiEndpoints):
    AuthRepository {

    override fun register(email: Credential): Single<TokenResponse> = apiEndpoints.register(email)
}