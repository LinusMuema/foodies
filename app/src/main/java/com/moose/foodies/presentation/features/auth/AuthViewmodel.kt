package com.moose.foodies.presentation.features.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators
import com.dsc.form_builder.Validators.*
import com.moose.foodies.FoodiesApplication
import com.moose.foodies.domain.models.Auth
import com.moose.foodies.domain.models.Credentials
import com.moose.foodies.domain.repositories.AuthRepository
import com.moose.foodies.domain.usecases.AuthUseCases
import com.moose.foodies.presentation.features.home.Screen
import com.moose.foodies.util.Result
import com.moose.foodies.util.parse
import com.moose.foodies.work.ItemWorker
import com.moose.foodies.work.RecipesWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AuthViewmodel @Inject constructor(private val authUseCases: AuthUseCases) : ViewModel() {

    private val _screen = mutableStateOf(0)
    val screen: State<Int> = _screen

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _result: MutableState<Result<String>> = mutableStateOf(Result.Idle(""))
    val result: State<Result<String>> = _result

    private val handler = CoroutineExceptionHandler { _, exception ->
        _loading.value = false
        _result.value = Result.Error(exception.parse())
    }

    val forgotFormState = FormState(
        fields = listOf(
            TextFieldState(
                name = "email",
                validators = listOf(Email(), Required()),
                transform = { it.trim().lowercase(Locale.getDefault()) }
            )
        )
    )

    val loginFormState = FormState(
        fields = listOf(
            TextFieldState(
                name = "password",
                validators = listOf(Required())
            ),
            TextFieldState(
                name = "email",
                validators = listOf(Email(), Required()),
                transform = { it.trim().lowercase(Locale.getDefault()) }
            )
        )
    )

    val signupFormState = FormState(
        fields = listOf(
            TextFieldState(
                name = "password",
                validators = listOf(Required()),
            ),
            TextFieldState(
                name = "confirm",
                validators = listOf(Required())
            ),
            TextFieldState(
                name = "email",
                validators = listOf(Email(), Required()),
                transform = { it.trim().lowercase(Locale.getDefault()) }
            )
        )
    )

    fun changeScreen(screen: Int) {
        _screen.value = screen
    }

    fun login() {
        viewModelScope.launch(handler) {
            _loading.value = true

            val credentials = loginFormState.getData(Credentials::class)
            val result = authUseCases.login(credentials)
            _result.value = Result.Success(result)

            _loading.value = false
        }
    }

    fun signup() {
        viewModelScope.launch(handler) {
            _loading.value = true

            val credentials = signupFormState.getData(Credentials::class)
            val result = authUseCases.signup(credentials)
            _result.value = Result.Success(result)

            _loading.value = false
        }
    }

    fun forgot() {
        viewModelScope.launch(handler) {
            _loading.value = true

            val email = forgotFormState.getState<TextFieldState>("email").value
            authUseCases.forgot(email)

            changeScreen(0)
            _result.value = Result.Error("check your email for code")

            _loading.value = false
        }
    }

    fun startWork() {
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

        // queue the work
        manager.enqueue(itemsWork)
    }
}
