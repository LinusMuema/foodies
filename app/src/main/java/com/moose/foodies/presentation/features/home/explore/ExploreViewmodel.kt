package com.moose.foodies.presentation.features.home.explore

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.domain.models.SearchData
import com.moose.foodies.domain.paging.RecipesSource
import com.moose.foodies.domain.repositories.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewmodel @Inject constructor(val repository: RecipeRepository): ViewModel() {

    init {
        getRecipes("")
    }

    private val _categories: MutableState<List<String>> = mutableStateOf(listOf())
    val categories: State<List<String>> = _categories

    lateinit var recipes: Flow<PagingData<Recipe>>

    fun addCategory(category: String) {
        _categories.value = _categories.value + category
    }

    fun removeCategory(category: String) {
        _categories.value = _categories.value - category
    }

    fun getRecipes(query: String, categories: List<String> = listOf()){
        val data = SearchData(name = query, categories = categories)
        recipes = Pager(PagingConfig(10)){ RecipesSource(data, repository)}.flow
    }
}