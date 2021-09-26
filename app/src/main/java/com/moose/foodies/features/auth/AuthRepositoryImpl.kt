package com.moose.foodies.features.auth

import com.moose.foodies.local.UserDao
import com.moose.foodies.models.Auth
import com.moose.foodies.models.Credentials
import com.moose.foodies.remote.ApiEndpoints
import com.moose.foodies.util.Preferences
import javax.inject.Inject

// the implementation of the service, i.e, the use cases
class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val preferences: Preferences,
    private val apiEndpoints: ApiEndpoints,
): AuthRepository {
    override fun setToken(token: String) = preferences.setToken(token)

    override suspend fun forgot(email: String) = apiEndpoints.forgot(email)

    override suspend fun login(credentials: Credentials): Auth {
        // make the network request
        val result = apiEndpoints.login(credentials)

        // save the records
        userDao.addProfile(result.user)
        preferences.setToken(result.token)

        return result
    }

    override suspend fun signup(credentials: Credentials): Auth {
        // make the network request
        val result = apiEndpoints.signup(credentials)

        // save the records
        userDao.addProfile(result.user)
        preferences.setToken(result.token)

        return result
    }
}