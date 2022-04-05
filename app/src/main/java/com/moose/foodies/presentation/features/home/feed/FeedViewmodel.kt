package com.moose.foodies.presentation.features.home.feed

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moose.foodies.domain.models.CompleteRecipe
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.domain.repositories.FeedRepository
import com.moose.foodies.domain.usecases.FeedUseCases
import com.moose.foodies.util.Result
import com.moose.foodies.util.parse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FeedViewmodel @Inject constructor(val repository: FeedRepository, private val feedUseCases: FeedUseCases) : ViewModel() {

    var type = "Snack"
    val profile = feedUseCases.getUserProfile()
    val time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val titles = mutableListOf("Breakfast", "Snack", "Main", "Other")

    private val _chefs: MutableState<List<Profile>> = mutableStateOf(listOf())
    val chefs: State<List<Profile>> = _chefs

    private val _featured: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val featured: State<List<Recipe>> = _featured

    private val _recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val recipes: State<List<Recipe>> = _recipes

    private val _seconds = mutableStateOf(0)
    val seconds: State<Int> = _seconds

    private val _refreshing = mutableStateOf(false)
    val refreshing: State<Boolean> = _refreshing

    init {
        getType()
        fetchData()
        startCounter()
        getRecipes(0)
    }

    private fun startCounter() {
        viewModelScope.launch {
            delay(5000)
            _seconds.value = 5
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _refreshing.value = true
            feedUseCases.refreshData()
            fetchData()
        }
    }

    fun getRecipes(index: Int){
        viewModelScope.launch {
            val categories = when {
                titles[index] == "Main" -> listOf("Lunch", "Dinner")
                titles[index] == "Other" -> listOf("Appetizer", "Drink")
                titles[index] == "Snack" -> listOf("Snack", "Salad", "Dessert")
                else -> listOf(titles[index])
            }
            _recipes.value = feedUseCases.getRecipesByCategory(categories)
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            _chefs.value = feedUseCases.getChefs()
            _featured.value = feedUseCases.getFeaturedRecipes()

            _refreshing.value = false
        }
    }

    private fun getType() {
        type = when {
            time >= 22 -> "Snack"
            time >= 18 -> "Main"
            time >= 15 -> "Snack"
            time >= 12 -> "Main"
            time >= 9 -> "Snack"
            time >= 4 -> "Breakfast"
            else -> "Snacks"
        }

        titles.remove(type)
    }
}