package com.moose.foodies.features.intolerances

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moose.foodies.models.AuthResponse
import com.moose.foodies.models.Intolerance
import com.moose.foodies.models.Intolerances
import com.moose.foodies.models.UiState
import com.moose.foodies.network.ApiRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.inject.Inject

class IntolerancesViewModel @Inject constructor(private val apiRepository: ApiRepository, private val sharedPreferences: SharedPreferences): ViewModel() {
    private val intolerances: ArrayList<Intolerance> = ArrayList()
    val results: MutableLiveData<List<Intolerance>> = MutableLiveData()
    val saveResult: MutableLiveData<Boolean> = MutableLiveData()
    val state: MutableLiveData<UiState> = MutableLiveData()
    private val composite = CompositeDisposable()

    fun getIntolerances() {
        composite.add(
            apiRepository.getIntolerances()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { results.value = it.intolerances },
                    {
                        when(it){
                            is HttpException -> {
                                val body = it.response()!!.errorBody()!!.string()
                                val error = GsonBuilder().create().fromJson(body, AuthResponse::class.java)
                                state.value = UiState(error.message, error.reason)
                            }
                            else -> state.value = UiState(it.message!!, null)
                        }
                    })
        )
    }

    fun update(){
        composite.add(
            apiRepository.updateIntolerances(Intolerances(intolerances, "posting"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { saveResult.value = it.updated },
                    {
                        when(it){
                            is HttpException -> {
                                val body = it.response()!!.errorBody()!!.string()
                                val error = GsonBuilder().create().fromJson(body, AuthResponse::class.java)
                                state.value = UiState(error.reason, null)
                            }
                            else -> state.value = UiState(it.message!!, null)
                        }
                    })
        )
    }
    fun handleItem(intolerance: Intolerance, selected: Boolean) {
        if (selected) intolerances.add(intolerance)
        else intolerances.remove(intolerance)
    }

    override fun onCleared() {
        super.onCleared()
    }
}