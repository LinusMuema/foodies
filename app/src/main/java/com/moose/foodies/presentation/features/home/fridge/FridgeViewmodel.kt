package com.moose.foodies.presentation.features.home.fridge

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moose.foodies.domain.models.Item
import com.moose.foodies.domain.repositories.FridgeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FridgeViewmodel @Inject constructor(private val repository: FridgeRepository): ViewModel() {

    init {
        getIngredients()
    }

    private val _ingredients: MutableState<List<Item>> = mutableStateOf(listOf())
    val ingredients: State<List<Item>> = _ingredients

    private val _selected: MutableState<List<Item>> = mutableStateOf(listOf())
    val selected: State<List<Item>> = _selected


    private fun getIngredients() {
        viewModelScope.launch {
            repository.ingredients.collect {
                _ingredients.value = it
            }
        }
    }
}