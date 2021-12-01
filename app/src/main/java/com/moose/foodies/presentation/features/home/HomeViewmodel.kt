package com.moose.foodies.presentation.features.home

import androidx.lifecycle.*
import com.moose.foodies.domain.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(val repository: HomeRepository): ViewModel() {
    private val _refreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    val refreshing: LiveData<Boolean> = _refreshing

    fun fetchData() {
        viewModelScope.launch {
            _refreshing.value = true
            repository.fetchData()
            _refreshing.value = false
        }
    }

    val profile = repository.profile.asLiveData()
}