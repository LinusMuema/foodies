package com.moose.foodies

import com.moose.foodies.data.local.UserDao
import com.moose.foodies.data.remote.AuthService
import com.moose.foodies.domain.models.Auth
import com.moose.foodies.domain.models.Credentials
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.repositories.AuthRepository
import com.moose.foodies.domain.repositories.AuthRepositoryImpl
import com.moose.foodies.util.Preferences
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class AuthRepositoryTest {
    val dao: UserDao = mockk()
    val api: AuthService = mockk()
    val preferences: Preferences = mockk()

    @Nested
    @DisplayName("Given the userdao, authservice and preferences are valid")
    inner class ValidRepository {
        val profile: Profile = mockk()
        val credentials: Credentials = mockk()

        val repository: AuthRepository = AuthRepositoryImpl(api, dao, preferences)

        @BeforeEach
        fun setResponses(){
            coEvery { api.login(credentials) } returns Auth(message =  "", token = "", user = profile)
        }

        @Nested
        @DisplayName("when a user logins")
        inner class UserLoggedIn {

            @BeforeEach
            fun `authenticate the user`(){
                runBlocking {
                    repository.login(credentials)
                }
            }

//            @Test
//            @DisplayName("web tokens must be saved in shared preferences")
//            fun `token must be saved`(){
//                val token = preferences.getToken()
//                assertNotNull(token)
//            }

            @Test
            @DisplayName("user profile must be saved in local db")
            suspend fun `profile must be saved`(){
                val profile = dao.getProfile().first()
                assertNotEquals(profile, null)
            }
        }
    }
}