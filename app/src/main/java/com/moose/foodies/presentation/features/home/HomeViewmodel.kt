package com.moose.foodies.presentation.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.moose.foodies.domain.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(repository: HomeRepository): ViewModel() {

    val profile = repository.profile.asLiveData()
}