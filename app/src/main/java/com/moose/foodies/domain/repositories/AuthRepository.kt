package com.moose.foodies.domain.repositories

import com.moose.foodies.data.models.Credentials

interface AuthRepository {

    suspend fun login(credentials: Credentials): String

    suspend fun signup(credentials: Credentials): String

    suspend fun forgot(email: String): String
}
