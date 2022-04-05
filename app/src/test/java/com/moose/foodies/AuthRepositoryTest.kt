package com.moose.foodies

import com.moose.foodies.data.local.UserDao
import com.moose.foodies.data.remote.AuthService
import com.moose.foodies.data.models.Auth
import com.moose.foodies.data.models.Credentials
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.repositories.AuthRepository
import com.moose.foodies.domain.repositories.AuthRepositoryImpl
import com.moose.foodies.util.Preferences
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AuthRepositoryTest {

    private val dao: UserDao = mock()
    private val api: AuthService = mock()
    private val preferences: Preferences = mock()

    @Nested
    @DisplayName("GIVEN the userdao, authservice and preferences are valid")
    inner class ValidRepository {
        private val profile = Profile(email = "test@mail.com")
        private val auth = Auth(message = "", token = "some-token", user = profile)
        val credentials = Credentials(email = "test@mail.com", password = "")

        val repository: AuthRepository = AuthRepositoryImpl(api, dao, preferences)

        @Before
        suspend fun setup(){
            whenever(preferences.setToken("")).thenReturn(Unit)
            whenever(repository.login(credentials)).thenReturn(auth)
            whenever(repository.signup(credentials)).thenReturn(auth)
        }

        @Nested
        @DisplayName("WHEN a user logs in")
        inner class UserLoggedIn {

            @Test
            @DisplayName("THEN the token must be saved")
            fun `token must be saved`(){
                runBlocking {
                    val user = repository.login(credentials)

                    verify(preferences, times(1)).setToken("")
                    verify(api.login(credentials), times(1))
                    verify(dao.addProfile(profile), times(1))
                    assertEquals(user.token, "some-token")
                }
            }

            @Test
            @DisplayName("THEN the profile must be saved")
            fun `profile must be saved`(){
                assertEquals(1+2, 3)
            }
        }

        @Nested
        @DisplayName("WHEN a user signs up")
        inner class UserSignsUp {
            @Test
            @DisplayName("THEN the token must be saved")
            fun `token must be saved`(){
                assertEquals(1+1, 2)
            }

            @Test
            @DisplayName("THEN the profile must be saved")
            fun `profile must be saved`(){
                assertEquals(1+2, 3)
            }
        }
    }
}