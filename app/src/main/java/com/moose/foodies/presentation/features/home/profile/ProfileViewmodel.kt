package com.moose.foodies.presentation.features.home.profile

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.work.WorkManager
import com.moose.foodies.FoodiesApplication
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.domain.repositories.ProfileRepository
import com.moose.foodies.domain.usecases.ProfileUseCases
import com.moose.foodies.util.parse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewmodel @Inject constructor(private val profileUseCases: ProfileUseCases): ViewModel() {

    var userId = ""
    val profile = profileUseCases.getProfile()
    val progress = profileUseCases.getProgress()

    private val _recipes: MutableState<List<Recipe>> = mutableStateOf(emptyList())
    val recipes: State<List<Recipe>> = _recipes

    private val _favorites: MutableState<List<Recipe>> = mutableStateOf(emptyList())
    val favorites: State<List<Recipe>> = _favorites

    private val _url: MutableState<String> = mutableStateOf("")
    val url: State<String> = _url

    private val _loading: MutableState<Boolean> = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _error: MutableState<String?> = mutableStateOf(null)
    val error: State<String?> = _error

    private val handler = CoroutineExceptionHandler { _, exception ->
        _error.value = exception.parse()
    }

    fun getData() {
        viewModelScope.launch(handler) {
            with(profile.first()){
                userId = id
                _recipes.value = profileUseCases.getRecipes(id)
                _favorites.value = profileUseCases.getFavorites(favorites)
            }
        }
    }

    fun uploadAvatar(uri: Uri) {
        viewModelScope.launch(handler) {
            val dir = "Foodies/users/$userId"
            profileUseCases.uploadImage(dir, uri)
        }
    }

    fun updateProfile(profile: Profile) {
        viewModelScope.launch(handler) {
            _loading.value = true

            profileUseCases.updateProfile(profile)
            profileUseCases.clearProgress()

            _loading.value = false
        }
    }

    fun logout() {
        val manager = WorkManager.getInstance(FoodiesApplication.appContext)

        viewModelScope.launch(handler) {
            profileUseCases.clearData()
            manager.cancelAllWork()
        }
    }
}