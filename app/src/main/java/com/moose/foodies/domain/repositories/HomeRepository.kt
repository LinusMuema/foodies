package com.moose.foodies.domain.repositories

import com.moose.foodies.data.local.UserDao
import com.moose.foodies.domain.models.Profile
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface HomeRepository {
    val profile: Flow<Profile>
}

class HomeRepositoryImpl @Inject constructor(private val userDao: UserDao): HomeRepository {
    override val profile: Flow<Profile>
        get() = userDao.getProfile()
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeRepositoryBinding {

    @Binds
    abstract fun provideRepository(impl: HomeRepositoryImpl): HomeRepository
}
