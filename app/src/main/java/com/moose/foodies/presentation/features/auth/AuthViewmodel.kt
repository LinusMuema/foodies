package com.moose.foodies.presentation.features.auth

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.moose.foodies.util.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.moose.foodies.FoodiesApplication
import com.moose.foodies.data.remote.AuthService
import com.moose.foodies.domain.models.Auth
import com.moose.foodies.domain.models.Credentials
import com.moose.foodies.domain.repositories.AuthRepository
import com.moose.foodies.presentation.components.Email
import com.moose.foodies.presentation.components.FormState
import com.moose.foodies.presentation.components.Required
import com.moose.foodies.presentation.components.TextFieldState
import com.moose.foodies.util.parse
import com.moose.foodies.work.ItemWorker
import com.moose.foodies.work.RecipesWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AuthViewmodel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _screen = mutableStateOf(0)
    val screen: State<Int> = _screen
    fun changeScreen(value: Int) = value.also { _screen.value = it }

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading
    fun changeLoading(value: Boolean) = value.also { _loading.value = it }

    private val _result = mutableStateOf<Result<Auth>?>(null)
    val result: State<Result<Auth>?> = _result

    private val handler = CoroutineExceptionHandler { _, exception ->
        _loading.value = false
        _result.value = Result.Error(exception.parse())
    }

    val loginFormState = FormState(
        fields = listOf(
            TextFieldState(name = "password", validators = listOf(Required())),
            TextFieldState<String>(name = "email", validators = listOf(Email(), Required()))
        )
    )

    fun login() {
        _loading.value = true
        val credentials = loginFormState.getData<Credentials>()

        viewModelScope.launch(handler) {
            val result = repository.login(credentials)
            _result.value = Result.Success(result)

            startWork()
        }
    }

    fun signup(email: String, password: String) {
        _loading.value = true
        viewModelScope.launch(handler) {
            val result = repository.signup(Credentials(email, password))
            _result.value = Result.Success(result)

            startWork()
        }
    }

    fun forgot(email: String) {
        _loading.value = true
        viewModelScope.launch(handler) {
            repository.forgot(email)

            changeScreen(0)
            _result.value = Result.Error("check your email for code")
        }
    }

    private fun startWork() {
        val manager = WorkManager.getInstance(FoodiesApplication.appContext)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        // get user's recipes
        val recipesWork = OneTimeWorkRequestBuilder<RecipesWorker>()
            .setConstraints(constraints)
            .build()
        manager.enqueue(recipesWork)

        // get the ingredients and recipes
        val itemsWork = PeriodicWorkRequest
            .Builder(ItemWorker::class.java, 6, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
        manager.enqueue(itemsWork)
    }
}