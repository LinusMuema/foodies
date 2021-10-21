package com.moose.foodies.features.profile

import androidx.lifecycle.LiveData
import com.moose.foodies.models.Profile
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    val profile: Flow<Profile>
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class ProfileRepositoryBinding(){

    @Binds
    abstract fun provideRepository(impl: ProfileRepositoryImpl): ProfileRepository
}