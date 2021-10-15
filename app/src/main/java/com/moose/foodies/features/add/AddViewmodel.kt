package com.moose.foodies.features.add

import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.moose.foodies.util.Cloudinary
import com.moose.foodies.util.UploadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewmodel @Inject constructor(private val cloudinary: Cloudinary): ViewModel() {

    private val _path: MutableLiveData<Uri> = MutableLiveData()
    fun setUri(uri: Uri) = uri.also { _path.value = it }
    val path: LiveData<Uri> = _path

    val progress: LiveData<UploadState> = cloudinary.progress

}