package com.moose.foodies.features.profile

import android.net.Uri
import androidx.lifecycle.*
import com.moose.foodies.models.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewmodel @Inject constructor(private val repository: ProfileRepository): ViewModel() {

    val progress = repository.progress
    val profile = repository.profile.asLiveData()

    private val _url: MutableLiveData<String> = MutableLiveData()
    val url: LiveData<String> = _url

    fun uploadAvatar(uri: Uri) {
        viewModelScope.launch {
            val dir = "Foodies/users/${repository.profile.first()._id}"
            repository.uploadImage(dir, uri)
        }
    }

    fun updateProfile(profile: Profile) {

    }
}