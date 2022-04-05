package com.moose.foodies.data.repositories

import com.moose.foodies.data.local.UserDao
import com.moose.foodies.data.remote.AuthService
import com.moose.foodies.data.models.Credentials
import com.moose.foodies.domain.repositories.AuthRepository
import com.moose.foodies.util.Preferences
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val authService: AuthService,
    private val preferences: Preferences
) : AuthRepository {

    override suspend fun forgot(email: String): String {
        authService.forgot(email)
        return "Check your email for further instructions"
    }

    override suspend fun login(credentials: Credentials): String {
        val result = authService.login(credentials)

        preferences.setToken(result.token)
        userDao.addProfile(result.user.copy(current = true))

        return "Login Successful"
    }

    override suspend fun signup(credentials: Credentials): String {
        val result = authService.signup(credentials)
        result.user.current = true

        userDao.addProfile(result.user)
        preferences.setToken(result.token)

        return "Signup Successful"
    }
}
