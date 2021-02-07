package com.moose.foodies.features.feature_ingredients

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moose.foodies.models.FridgeRecipe
import javax.inject.Inject

class IngredientsViewModel @Inject constructor(): ViewModel() {

    val instructions: MutableLiveData<FridgeRecipe> = MutableLiveData()

    fun getRecipes(ingredients: String) {

    }

    fun getRecipeById(id: String){
    }

    override fun onCleared() {
        super.onCleared()
    }
}