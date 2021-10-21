package com.moose.foodies.features.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewmodel @Inject constructor(private val repository: ProfileRepository): ViewModel() {

}