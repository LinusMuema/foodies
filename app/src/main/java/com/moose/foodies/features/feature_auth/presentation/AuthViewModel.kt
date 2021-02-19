package com.moose.foodies.features.feature_auth.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.moose.foodies.FoodiesApplication
import com.moose.foodies.features.feature_auth.data.AuthRepository
import com.moose.foodies.features.feature_auth.domain.Credential
import com.moose.foodies.features.feature_auth.domain.TokenResponse
import com.moose.foodies.features.feature_auth.work.UpdateWorker
import com.moose.foodies.util.Result
import com.moose.foodies.util.parse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class AuthViewModel @Inject constructor(private val repository: AuthRepository, ): ViewModel() {

    private val composite = CompositeDisposable()

    private val _token = MutableLiveData<Result<TokenResponse>>()
    val token: LiveData<Result<TokenResponse>> = _token

    fun register(mail: String) {
        val credential = Credential(email = mail)
        composite.add(
            repository.register(credential)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _token.postValue(Result.Success(it)) },
                    { _token.postValue(Result.Error(it.parse())) }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }

    fun getBackup() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val work = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(FoodiesApplication.getInstance()).enqueue(work)
    }
}