package com.moose.foodies.features.feature_ingredients

import androidx.lifecycle.ViewModel
import com.moose.foodies.di.ActivityScope
import com.moose.foodies.di.ViewModelKey
import com.moose.foodies.features.feature_ingredients.data.IngredientsRepository
import com.moose.foodies.features.feature_ingredients.data.IngredientsRepositoryImpl
import com.moose.foodies.features.feature_ingredients.presentation.IngredientsActivity
import com.moose.foodies.features.feature_ingredients.presentation.IngredientsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class IngredientsModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideIngredients(): IngredientsActivity

    @Binds
    @IntoMap
    @ViewModelKey(IngredientsViewModel::class)
    abstract fun bindIngredientsViewModel(fridgeViewModel: IngredientsViewModel): ViewModel

    @Binds
    abstract fun provideIngredientsRepository(impl: IngredientsRepositoryImpl): IngredientsRepository

}