package com.moose.foodies.presentation.features.recipe

import android.util.Log
import androidx.lifecycle.*
import com.moose.foodies.domain.models.Item
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.domain.repositories.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewmodel @Inject constructor(val repository: RecipeRepository): ViewModel() {

    private val _favorite: MutableLiveData<Boolean> = MutableLiveData(false)
    val favorite: LiveData<Boolean> = _favorite

    private val _recipe: MutableLiveData<Recipe> = MutableLiveData()
    val recipe: LiveData<Recipe> = _recipe

    private val _ingredients: MutableLiveData<List<Item>> = MutableLiveData()
    val ingredients: LiveData<List<Item>> = _ingredients

    private val _equipment: MutableLiveData<List<Item>> = MutableLiveData()
    val equipment: LiveData<List<Item>> = _equipment


    fun setChef(user: Profile) = repository.setChef(user)

    fun checkFavorite(id: String){
        viewModelScope.launch {
            val favorite = repository.getFavorite(id)
            _favorite.value = favorite != null
        }
    }

    fun getRecipe(id: String){
        viewModelScope.launch {
            val item = repository.getRecipe(id)

            _recipe.value = item
            _equipment.value = item.equipment.map { repository.getItem(it) }
            _ingredients.value = item.ingredients.map { repository.getItem(it) }

        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val recipe = _recipe.value!!
            if (_favorite.value!!) recipe.type = "FAVORITE"

            repository.updateRecipe(recipe)
            _favorite.value = !_favorite.value!!
        }
    }
}