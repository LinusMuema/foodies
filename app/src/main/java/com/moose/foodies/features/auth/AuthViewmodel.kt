package com.moose.foodies.features.auth

import com.moose.foodies.util.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moose.foodies.models.Auth
import com.moose.foodies.models.Credentials
import com.moose.foodies.remote.ApiEndpoints
import com.moose.foodies.util.Preferences
import com.moose.foodies.util.parse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewmodel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {

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
            val result = authRepository.login(Credentials(email, password))
            _result.value = Result.Success(result)
        }
    }

    fun forgot(email: String) {
        _loading.value = true
        viewModelScope.launch(handler) {
            authRepository.forgot(email)

            changeScreen(0)
            _result.value = Result.Error("check your email for code")
        }
    }

    fun signup(email: String, password: String) {
        _loading.value = true
        viewModelScope.launch(handler) {
            val result = authRepository.signup(Credentials(email, password))
            _result.value = Result.Success(result)
        }
    }
}