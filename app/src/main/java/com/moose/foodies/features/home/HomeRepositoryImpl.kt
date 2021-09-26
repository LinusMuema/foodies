package com.moose.foodies.features.home

import com.moose.foodies.local.UserDao
import com.moose.foodies.models.Profile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val userDao: UserDao): HomeRepository {
    override val profile: Flow<Profile>
        get() = userDao.getProfile()

}