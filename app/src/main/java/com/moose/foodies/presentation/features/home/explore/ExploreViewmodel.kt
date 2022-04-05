package com.moose.foodies.presentation.features.home.explore

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.data.models.SearchData
import com.moose.foodies.domain.repositories.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewmodel @Inject constructor(val repository: RecipeRepository): ViewModel() {

    init {
        getRecipes("")
    }

    private val _categories: MutableState<List<String>> = mutableStateOf(listOf())
    val categories: State<List<String>> = _categories

    private val _count: MutableState<List<Int>> = mutableStateOf(listOf())
    val count: State<List<Int>> = _count

    private val _recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val recipes: State<List<Recipe>> = _recipes

    fun addCategory(category: String) {
        _categories.value = _categories.value + category
    }

    fun removeCategory(category: String) {
        _categories.value = _categories.value - category
    }

    fun getRecipes(query: String, categories: List<String> = listOf()){
        val data = SearchData(name = query, categories = categories)
        viewModelScope.launch {
//            val recipes = repository.searchRecipe(data)
//            _recipes.value = recipes
//            _count.value = listOf(0)
        }
    }
}