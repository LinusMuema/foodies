package com.moose.foodies.features.add

import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.moose.foodies.models.Item
import com.moose.foodies.util.Cloudinary
import com.moose.foodies.util.UploadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AddViewmodel @Inject constructor(private val cloudinary: Cloudinary, private val repository: AddRepository): ViewModel() {

    private val _path: MutableLiveData<Uri> = MutableLiveData()
    fun setUri(uri: Uri) = uri.also { _path.value = it }
    val path: LiveData<Uri> = _path


    val progress: LiveData<UploadState> = cloudinary.progress

    fun getItems(name: String, type: String): List<Item> {
        return runBlocking{ repository.getItems(name, type) }
    }

    fun uploadImage() {
        viewModelScope.launch {
            cloudinary.uploadImage(_path.value!!)
        }
    }

    fun uploadRecipe(
        url: String,
        time: String,
        name: String,
        steps: List<String>,
        description: String,
        equipment: Set<Item>,
        ingredients: Set<Item>
    ) {
    }
}