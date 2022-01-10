package com.moose.foodies.domain.repositories

import com.moose.foodies.data.local.UserDao
import com.moose.foodies.data.remote.ApiEndpoints
import com.moose.foodies.data.remote.AuthService
import com.moose.foodies.domain.models.Auth
import com.moose.foodies.domain.models.Credentials
import com.moose.foodies.util.Preferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.ResponseBody
import javax.inject.Inject

interface AuthRepository {

    fun setToken(token: String)

    suspend fun login(credentials: Credentials): Auth

    suspend fun signup(credentials: Credentials): Auth

    suspend fun forgot(email: String): Any
}

class AuthRepositoryImpl @Inject constructor(val userDao: UserDao, val preferences: Preferences, val apiEndpoints: ApiEndpoints): AuthRepository {
    override fun setToken(token: String) = preferences.setToken(token)

    override suspend fun forgot(email: String) = AuthService.forgot(email)

    override suspend fun login(credentials: Credentials): Auth {
        val result = AuthService.login(credentials)

        preferences.setToken(result.token)
        userDao.addProfile(result.user.copy(current = true))

        return result
    }

    override suspend fun signup(credentials: Credentials): Auth {
        val result = AuthService.signup(credentials)

        userDao.addProfile(result.user)
        preferences.setToken(result.token)

        return result
    }
}
