package com.moose.foodies.features.auth

import com.moose.foodies.FoodiesApplication
import com.moose.foodies.features.BaseViewModel
import com.moose.foodies.models.Credentials
import com.moose.foodies.util.ExceptionParser
import com.moose.foodies.util.PreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class AuthViewModel @Inject constructor(): BaseViewModel() {

    fun startAuth(password: String, email: String) {
        val credentials = Credentials(email, password)
        composite.add(
            apiRepository.login(credentials)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        PreferenceHelper.setAccessToken(FoodiesApplication.getInstance(), it.token)
                        PreferenceHelper.setLogged(FoodiesApplication.getInstance(), true)
                        response.value = it.type
                    },
                    {
                        exception.value = ExceptionParser.parse(it)
                    })
        )
    }
}