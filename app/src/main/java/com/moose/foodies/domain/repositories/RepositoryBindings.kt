package com.moose.foodies.domain.repositories

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryBindings {

    @Binds
    abstract fun provideAddRepository(impl: AddRepositoryImpl): AddRepository

    @Binds
    abstract fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideChefRepository(impl: ChefRepositoryImpl): ChefRepository

    @Binds
    abstract fun provideHomeRepository(impl: FeedRepositoryImpl): FeedRepository

    @Binds
    abstract fun provideProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun provideRecipeRepository(impl: RecipeRepositoryImpl): RecipeRepository
}