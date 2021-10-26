package com.moose.foodies.features.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewmodel @Inject constructor(private val repository: ProfileRepository): ViewModel() {

    val profile = repository.profile.asLiveData()

    private val _path: MutableLiveData<Uri> = MutableLiveData()
    fun setUri(uri: Uri) = uri.also { _path.value = it }
    val path: LiveData<Uri> = _path

}