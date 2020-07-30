package com.moose.foodies.features.reset

import com.moose.foodies.di.network.ApiRepository
import com.moose.foodies.features.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ResetPasswordViewModel @Inject constructor(private val apiRepository: ApiRepository): BaseViewModel() {

    fun resetPassword(email: String) {
        composite.add(
            apiRepository.reset(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response.value = it.reason },
                    { exception.value = it.message })
        )
    }
}