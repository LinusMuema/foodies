package com.moose.foodies.presentation.features.recipe

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moose.foodies.domain.models.CompleteRecipe
import com.moose.foodies.domain.repositories.RecipeRepository
import com.moose.foodies.domain.usecases.RecipeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewmodel @Inject constructor(val repository: RecipeRepository,private val recipeUseCases: RecipeUseCases): ViewModel() {

    val profile = recipeUseCases.getProfile()

    private val _favorite: MutableState<Boolean> = mutableStateOf(false)
    val favorite: State<Boolean> = _favorite

    private val _recipe: MutableState<CompleteRecipe?> = mutableStateOf(null)
    val recipe: State<CompleteRecipe?> = _recipe

    fun checkFavorite(id: String){
        viewModelScope.launch {
            _favorite.value = profile.first().favorites.contains(id)
        }
    }

    fun getRecipe(id: String){
        viewModelScope.launch {
            _recipe.value = recipeUseCases.getRecipe(id)
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val profile = profile.first()
            val recipe = _recipe.value!!.id

            if (_favorite.value) {
                profile.favorites = profile.favorites - recipe
                _favorite.value = false
            } else {
                profile.favorites = profile.favorites + recipe
                _favorite.value = true
            }

            repository.updateProfile(profile)
        }
    }
}