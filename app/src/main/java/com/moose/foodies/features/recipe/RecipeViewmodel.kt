package com.moose.foodies.features.recipe

import androidx.lifecycle.*
import com.moose.foodies.models.Item
import com.moose.foodies.models.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewmodel @Inject constructor(val repository: RecipeRepository): ViewModel() {

    private val _recipe: MutableLiveData<Recipe> = MutableLiveData()
    val recipe: LiveData<Recipe> = _recipe

    private val _ingredients: MutableLiveData<List<Item>> = MutableLiveData()
    val ingredients: LiveData<List<Item>> = _ingredients

    private val _equipment: MutableLiveData<List<Item>> = MutableLiveData()
    val equipment: LiveData<List<Item>> = _equipment

    fun getRecipe(id: String){
        viewModelScope.launch {
            val item = repository.getRecipe(id)

            _recipe.value = item
            _equipment.value = item.equipment.map { repository.getItem(it) }
            _ingredients.value = item.ingredients.map { repository.getItem(it) }

        }
    }
}