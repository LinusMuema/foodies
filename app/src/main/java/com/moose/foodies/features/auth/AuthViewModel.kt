package com.moose.foodies.features.auth

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moose.foodies.FoodiesApplication
import com.moose.foodies.models.AuthResponse
import com.moose.foodies.models.Credentials
import com.moose.foodies.network.ApiRepository
import com.moose.foodies.util.PreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.inject.Inject


class AuthViewModel @Inject constructor(private val apiRepository: ApiRepository): ViewModel() {
    private val composite = CompositeDisposable()
    val response: MutableLiveData<String> = MutableLiveData()

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
                        when(it){
                            is HttpException -> {
                                val body = it.response()!!.errorBody()!!.string()
                                val error = GsonBuilder().create().fromJson(body, AuthResponse::class.java)
                                response.value = error.reason
                            }
                            else -> response.value = it.message
                        }
                    })
        )
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }
}