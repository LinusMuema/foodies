package com.moose.foodies.features.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class AuthViewmodel: ViewModel() {

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email
    fun changeEmail(value: String)  = value.also { _email.value = it }

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password
    fun changePassword(value: String) = value.also { _password.value = it }

    private val _toggle = MutableLiveData(false)
    val toggle: LiveData<Boolean> = _toggle
    fun changeToggle(value: Boolean) = value.also { _toggle.value = it }

    private val _screen = MutableLiveData(0)
    val screen: LiveData<Int> = _screen
    fun changeScreen(value: Int) = value.also { _screen.value = it }


}