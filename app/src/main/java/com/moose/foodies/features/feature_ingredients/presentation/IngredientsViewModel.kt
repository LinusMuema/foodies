package com.moose.foodies.features.feature_ingredients.presentation

import androidx.lifecycle.ViewModel
import com.moose.foodies.features.feature_ingredients.data.IngredientsRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class IngredientsViewModel @Inject constructor(private val repository: IngredientsRepository): ViewModel() {
    private val composite = CompositeDisposable()

    fun getRecipes(ingredients: String) {
    }

    fun getRecipeById(id: String){
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }
}