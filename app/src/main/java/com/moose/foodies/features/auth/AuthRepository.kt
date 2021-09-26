package com.moose.foodies.features.auth

import com.moose.foodies.models.Auth
import com.moose.foodies.models.Credentials
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.ResponseBody

// the methods in the auth service
interface AuthRepository {

    fun setToken(token: String)

    suspend fun login(credentials: Credentials): Auth

    suspend fun signup(credentials: Credentials): Auth

    suspend fun forgot(email: String): ResponseBody
}

// bind the implementation to the interface
@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthRepositoryBinding {

    @Binds
    abstract fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}

