package com.moose.foodies.features.reset

import com.moose.foodies.features.BaseViewModel
import com.moose.foodies.util.ExceptionParser
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ResetPasswordViewModel @Inject constructor(): BaseViewModel() {

    fun resetPassword(email: String) {
        composite.add(
            apiRepository.reset(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response.value = it.reason },
                    { exception.value = ExceptionParser.parse(it) })
        )
    }
}