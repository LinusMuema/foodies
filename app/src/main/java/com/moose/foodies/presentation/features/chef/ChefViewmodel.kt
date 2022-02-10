package com.moose.foodies.presentation.features.chef

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.domain.repositories.ChefRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChefViewmodel @Inject constructor(val repository: ChefRepository): ViewModel() {

    init {
       startCounter()
    }

    private val _chef: MutableState<Profile?> = mutableStateOf(null)
    val chef: State<Profile?> = _chef

    private val _recipes: MutableState<List<Recipe>?> = mutableStateOf(null)
    val recipes: State<List<Recipe>?> = _recipes

    private val _seconds = mutableStateOf(0)
    val seconds: State<Int> = _seconds

    private fun startCounter() {
        viewModelScope.launch {
            delay(3000)
            _seconds.value = 3
        }
    }

    fun getChef(id: String?) {
        viewModelScope.launch {
            getRecipes(id!!)
            _chef.value = repository.getChef(id)
        }
    }

    private suspend fun getRecipes(id: String){
        _recipes.value = repository.getChefRecipes(id)
    }
}