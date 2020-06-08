package com.moose.foodies.features.home

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.moose.foodies.db.DbRepository
import com.moose.foodies.models.AuthResponse
import com.moose.foodies.models.Recipe
import com.moose.foodies.models.Recipes
import com.moose.foodies.models.UiState
import com.moose.foodies.network.ApiRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val apiRepository: ApiRepository, private val dbRepository: DbRepository, private val sharedPreferences: SharedPreferences): ViewModel() {

    private val composite = CompositeDisposable()
    private val token = sharedPreferences.getString("token", "default")
    val recipes: MutableLiveData<List<Recipe>> = MutableLiveData()
    val state: MutableLiveData<UiState> = MutableLiveData()

    fun getRecipes(){
        composite.add(
            dbRepository.getTodaysRecipes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .doOnError { state.value = UiState(it.cause.toString(), it.message) }
                .subscribe
                    {
                        if(it.isEmpty()) updateRecipes()
                        else recipes.value = it[0].recipes
                    }
        )
    }

    fun updateRecipes() {
        composite.add(
            apiRepository.getRecipes("Bearer $token")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        updateDbRecipes(it)
                    },
                    {
                        when(it){
                            is HttpException -> {
                                val body = it.response()!!.errorBody()!!.string()
                                val error = GsonBuilder().create().fromJson(body, AuthResponse::class.java)
                                state.value = UiState(error.message, error.reason)
                            }
                            else -> state.value = UiState(it.cause.toString(), it.message!!)
                        }
                }))
    }

    private fun updateDbRecipes(data: Recipes){
        composite.add(
            dbRepository.deleteRecipes()
                .andThen(dbRepository.deleteRecipes())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .doOnComplete {
                    Log.d("recipe", "deleted recipes from db")
                    composite.add(
                        dbRepository.insertTodaysRecipes(data)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.computation())
                            .subscribe(
                                {
                                    recipes.value = data.recipes
                                },
                                {
                                    state.value = UiState(it.cause.toString(), it.message)
                                }))
                }
                .subscribe()
        )
    }

    fun logout(){
        sharedPreferences.edit().putBoolean("logged", false).apply()
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }
}