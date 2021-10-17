package com.moose.foodies.features.add

import androidx.lifecycle.LiveData
import com.moose.foodies.models.Item
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

interface AddRepository {
    suspend fun getItems(name: String,  type: String): List<Item>
}


@Module
@InstallIn(ViewModelComponent::class)
abstract class AddRepositoryBinding {

    @Binds
    abstract fun provideAddRepository(impl: AddRepositoryImpl): AddRepository
}