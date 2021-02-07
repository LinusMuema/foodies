package com.moose.foodies.features.feature_ingredients

import androidx.lifecycle.ViewModel
import com.moose.foodies.di.ActivityScope
import com.moose.foodies.di.ViewModelKey
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
}