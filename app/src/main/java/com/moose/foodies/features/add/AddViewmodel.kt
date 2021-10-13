package com.moose.foodies.features.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moose.foodies.util.Cloudinary
import com.moose.foodies.util.UploadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewmodel @Inject constructor(private val cloudinary: Cloudinary): ViewModel() {

    val progress: LiveData<UploadState> = cloudinary.progress

    suspend fun uploadImage(path: String){
        viewModelScope.launch {
            cloudinary.uploadImage(path)
        }
    }
}