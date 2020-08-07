package com.moose.foodies.features.recipe

import androidx.lifecycle.MutableLiveData
import com.moose.foodies.features.BaseViewModel
import com.moose.foodies.models.Recipe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecipeViewModel @Inject constructor(): BaseViewModel() {

    val isFavorite: MutableLiveData<Boolean> = MutableLiveData()

    fun checkFavorite(id: Int){
        composite.add(
            dbRepository.getOneFavorite(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(
                    {
                        isFavorite.value = true
                    },
                    {
                        if ("empty result" in it.message!!) isFavorite.value = false
                        else exception.value = it.message
                    })
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

    fun addFavorite(recipe: Recipe){
        composite.add(
            dbRepository.getFavoritesCount()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(
                    {
                        if (it >= 10) exception.value = "Favorites limit reached"
                        else insertFavorite(recipe)
                    },
                    {exception.value = it.message})
        )
    }

    private fun insertFavorite(recipe: Recipe) {
        composite.add(
            dbRepository.insertFavorite(recipe)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(
                    {
                        isFavorite.value = true
                        exception.value = "Added recipe to favorites"
                    },
                    {exception.value = it.message})
        )
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }

}