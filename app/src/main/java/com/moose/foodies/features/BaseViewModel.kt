package com.moose.foodies.features

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BaseViewModel @Inject constructor(): ViewModel() {
    val composite = CompositeDisposable()
    val exception: MutableLiveData<String> = MutableLiveData()
    val response = MutableLiveData<Any>()

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }
}
