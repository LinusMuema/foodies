package com.moose.foodies.features.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moose.foodies.models.Result
import com.moose.foodies.models.State
import com.moose.foodies.util.ExceptionParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class AuthViewModel @Inject constructor(private val repository: AuthRepository, ): ViewModel() {

    private val composite = CompositeDisposable()

    private val _token = MutableLiveData<Result<TokenResponse>>()
    val token: LiveData<Result<TokenResponse>> = _token

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun register(email: String) {
        val credential = Credential(email)
        _state.postValue(State.LOADING)
        composite.add(
            repository.register(credential)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _state.postValue(State.SUCCESS)
                        _token.postValue(Result.Success(it))
                    },
                    {
                        val message = ExceptionParser.parse(it)
                        _state.postValue(State.ERROR)
                        _token.postValue(Result.Error(message))
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }
}