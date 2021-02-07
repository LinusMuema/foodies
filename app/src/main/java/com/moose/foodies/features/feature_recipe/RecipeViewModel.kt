package com.moose.foodies.features.feature_recipe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moose.foodies.models.Recipe
import javax.inject.Inject

class RecipeViewModel @Inject constructor(): ViewModel() {

    val isFavorite: MutableLiveData<Boolean> = MutableLiveData()

    fun checkFavorite(id: Int){
    }

    fun removeFavorite(recipe: Recipe){
    }

    fun addFavorite(recipe: Recipe){

    }

    private fun insertFavorite(recipe: Recipe) {
    }

    override fun onCleared() {
        super.onCleared()
    }

}