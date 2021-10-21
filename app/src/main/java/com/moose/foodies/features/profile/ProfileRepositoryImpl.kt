package com.moose.foodies.features.profile

import com.moose.foodies.local.UserDao
import com.moose.foodies.remote.ApiEndpoints
import com.moose.foodies.util.Cloudinary
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dao: UserDao,
    private val api: ApiEndpoints,
    private val cloudinary: Cloudinary
): ProfileRepository {
}