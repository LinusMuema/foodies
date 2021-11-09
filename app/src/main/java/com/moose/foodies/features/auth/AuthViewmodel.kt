package com.moose.foodies.features.auth

import com.moose.foodies.util.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.moose.foodies.FoodiesApplication
import com.moose.foodies.models.Auth
import com.moose.foodies.models.Credentials
import com.moose.foodies.util.parse
import com.moose.foodies.work.RecipesWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewmodel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _screen = MutableLiveData(0)
    val screen: LiveData<Int> = _screen
    fun changeScreen(value: Int) = value.also { _screen.value = it }

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading
    fun changeLoading(value: Boolean) = value.also { _loading.value = it }

    private val _result = MutableLiveData<Result<Auth>>()
    val result: LiveData<Result<Auth>> = _result

    private val handler = CoroutineExceptionHandler { _, exception ->
        _loading.value = false
        _result.value = Result.Error(exception.parse())
    }

    fun login(email: String, password: String) {
        _loading.value = true
        viewModelScope.launch(handler) {
            val result = repository.login(Credentials(email, password))
            _result.value = Result.Success(result)
            getRecipes()
        }
    }

    private fun getRecipes() {
        val manager = WorkManager.getInstance(FoodiesApplication.appContext)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()
        val work = OneTimeWorkRequestBuilder<RecipesWorker>()
            .setConstraints(constraints)
            .build()
        manager.enqueue(work)
    }

    fun forgot(email: String) {
        _loading.value = true
        viewModelScope.launch(handler) {
            repository.forgot(email)

            changeScreen(0)
            _result.value = Result.Error("check your email for code")
        }
    }

    fun signup(email: String, password: String) {
        _loading.value = true
        viewModelScope.launch(handler) {
            val result = repository.signup(Credentials(email, password))
            _result.value = Result.Success(result)
        }
    }
}