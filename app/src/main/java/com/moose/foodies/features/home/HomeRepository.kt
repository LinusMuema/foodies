package com.moose.foodies.features.home

import com.moose.foodies.models.Profile
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    val profile: Flow<Profile>
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeRepositoryBinding {

    @Binds
    abstract fun provideRepository(impl: HomeRepositoryImpl): HomeRepository
}
