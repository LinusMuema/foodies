package com.moose.foodies.features.reset

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.moose.foodies.models.AuthResponse
import com.moose.foodies.network.ApiRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.inject.Inject

class ResetPasswordViewModel @Inject constructor(private val apiRepository: ApiRepository): ViewModel() {
    private val composite = CompositeDisposable()
    val response: MutableLiveData<String> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun resetPassword(email: String) {
        composite.add(
            apiRepository.reset(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        response.value = it.reason
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
}