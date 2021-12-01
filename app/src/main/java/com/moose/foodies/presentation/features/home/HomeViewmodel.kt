package com.moose.foodies.presentation.features.home

import androidx.lifecycle.*
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.domain.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(val repository: HomeRepository): ViewModel() {

    var type = "Snack"
    val time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val profile: LiveData<Profile> = repository.profile.asLiveData()
    val chefs: LiveData<List<Profile>> = repository.chefs.asLiveData()
    val recipes: LiveData<List<Recipe>> = repository.feed.asLiveData()

    private val _refreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    val refreshing: LiveData<Boolean> = _refreshing

    init {
        type =  when {
            time >= 22 -> "Snacks"
            time >= 18 -> "Main"
            time >= 15 -> "Snacks"
            time >= 12 -> "Main"
            time >= 9 -> "Snacks"
            time >= 4 -> "Breakfast"
            else -> "Snacks"
        }
    }

    fun fetchData() {
        viewModelScope.launch {
            _refreshing.value = true
            repository.fetchData()
            _refreshing.value = false
        }
    }
}