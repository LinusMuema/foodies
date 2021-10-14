package com.moose.foodies.features.add

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.moose.foodies.util.Cloudinary
import com.moose.foodies.util.UploadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewmodel @Inject constructor(private val cloudinary: Cloudinary): ViewModel() {

    val progress: LiveData<UploadState> = cloudinary.progress

    fun uploadImage(path: Uri){
        viewModelScope.launch {
            cloudinary.uploadImage(path)
        }
    }
}