package com.moose.foodies.features.profile

import android.net.Uri
import androidx.lifecycle.*
import com.moose.foodies.models.Profile
import com.moose.foodies.util.Result
import com.moose.foodies.util.parse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewmodel @Inject constructor(private val repository: ProfileRepository): ViewModel() {

    val progress = repository.progress
    val profile = repository.profile.asLiveData()
    val recipes = repository.recipes.asLiveData()

    private val _url: MutableLiveData<String> = MutableLiveData()
    val url: LiveData<String> = _url

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val handler = CoroutineExceptionHandler { _, exception ->
        _error.value = exception.parse()
    }

    fun uploadAvatar(uri: Uri) {
        viewModelScope.launch(handler) {
            val dir = "Foodies/users/${repository.profile.first()._id}"
            repository.uploadImage(dir, uri)
        }
    }

    fun updateProfile(profile: Profile) {
        viewModelScope.launch(handler) {
            _loading.value = true
            repository.updateProfile(profile)
            repository.clearProgress()
            _loading.value = false
        }
    }
}