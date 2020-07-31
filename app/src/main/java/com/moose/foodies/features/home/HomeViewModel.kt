package com.moose.foodies.features.home

import com.moose.foodies.db.DbRepository
import com.moose.foodies.di.network.ApiRepository
import com.moose.foodies.features.BaseViewModel
import com.moose.foodies.models.Recipes
import com.moose.foodies.util.ExceptionParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val apiRepository: ApiRepository, private val dbRepository: DbRepository): BaseViewModel() {

    fun getRecipes(){
        composite.add(
            dbRepository.getTodaysRecipes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe ({
                        if(it.isEmpty()) updateRecipes()
                        else response.value = it[0]
                    }, {exception.value = it.message})
        )
    }

    fun updateRecipes() {
        composite.add(
            apiRepository.getRecipes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { updateDbRecipes(it) },
                    { exception.value = ExceptionParser.parse(it) }))
    }

    private fun updateDbRecipes(data: Recipes){
        composite.add(
            dbRepository.deleteRecipes()
                .andThen(dbRepository.deleteRecipes())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .doOnComplete {
                    composite.add(
                        dbRepository.insertTodaysRecipes(data)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.computation())
                            .subscribe(
                                { response.value = data },
                                { exception.value = it.message }))
                }
                .subscribe()
        )
    }
}