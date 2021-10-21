package com.moose.foodies.features.profile

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface ProfileRepository {
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class ProfileRepositoryBinding(){

    @Binds
    abstract fun provideRepository(impl: ProfileRepositoryImpl): ProfileRepository
}