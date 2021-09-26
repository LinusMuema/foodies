package com.moose.foodies.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(repository: HomeRepository): ViewModel() {

    val profile = repository.profile.asLiveData()
}