package com.moose.foodies.features.favorites

import com.moose.foodies.db.DbRepository
import com.moose.foodies.features.BaseViewModel
import com.moose.foodies.models.Recipe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(private val dbRepository: DbRepository): BaseViewModel() {

    fun getFavorites(){
        composite.add(
            dbRepository.getFavorites()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(
                    {
                        if (it.isEmpty()) exception.value = "No favorites available"
                        else response.value = it
                    },
                    {exception.value = it.message})
        )
    }

    fun removeFavorite(recipe: Recipe){
        composite.add(
            dbRepository.deleteFavorite(recipe)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe()
        )
    }
}