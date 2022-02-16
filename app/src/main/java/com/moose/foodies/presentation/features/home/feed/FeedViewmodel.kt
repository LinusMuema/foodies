package com.moose.foodies.presentation.features.home.feed

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.domain.repositories.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FeedViewmodel @Inject constructor(val repository: FeedRepository) : ViewModel() {

    var type = "Snack"
    val time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    private val _profile: MutableState<Profile?> = mutableStateOf(null)
    val profile: State<Profile?> = _profile

    private val _chefs: MutableState<List<Profile>?> = mutableStateOf(listOf())
    val chefs: State<List<Profile>?> = _chefs

    private val _recipes: MutableState<List<Recipe>?> = mutableStateOf(null)
    val recipes: State<List<Recipe>?> = _recipes

    private val _seconds = mutableStateOf(0)
    val seconds: State<Int> = _seconds

    private val _refreshing = mutableStateOf(false)
    val refreshing: State<Boolean> = _refreshing

    init {
        getType()
        fetchData()
        listenToDb()
        startCounter()
    }

    private fun startCounter() {
        viewModelScope.launch {
            delay(5000)
            _seconds.value = 5
        }
    }

    private fun listenToDb() {
        viewModelScope.launch {
            launch { repository.profile.collect { _profile.value = it } }
            launch { repository.chefs.collect { _chefs.value = it } }
            launch { repository.recipes.collect { _recipes.value = it } }
        }
    }

    fun refresh() {
        _refreshing.value = true
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            repository.fetchData()
            _refreshing.value = false
        }
    }

    private fun getType() {
        type = when {
            time >= 22 -> "Snacks"
            time >= 18 -> "Main"
            time >= 15 -> "Snacks"
            time >= 12 -> "Main"
            time >= 9 -> "Snacks"
            time >= 4 -> "Breakfast"
            else -> "Snacks"
        }
    }
}