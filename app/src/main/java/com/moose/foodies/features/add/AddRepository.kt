package com.moose.foodies.features.add

import com.moose.foodies.models.Item
import com.moose.foodies.models.RawRecipe
import com.moose.foodies.models.Recipe
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface AddRepository {
    suspend fun getItems(name: String,  type: String): List<Item>

    suspend fun uploadRecipe(recipe: RawRecipe): Recipe
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class AddRepositoryBinding {

    @Binds
    abstract fun provideAddRepository(impl: AddRepositoryImpl): AddRepository
}