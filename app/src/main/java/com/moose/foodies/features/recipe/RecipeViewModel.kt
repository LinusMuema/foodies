package com.moose.foodies.features.recipe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moose.foodies.db.DbRepository
import com.moose.foodies.models.Recipe
import com.moose.foodies.models.UiState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecipeViewModel @Inject constructor(private val dbRepository: DbRepository): ViewModel() {

    private val composite = CompositeDisposable()
    val isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val state: MutableLiveData<UiState> = MutableLiveData()

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
                        else state.value = UiState(it.cause.toString(), it.message!!)
                    })
        )
    }

    fun removeFavorite(recipe: Recipe){
        composite.add(
            dbRepository.deleteFavorite(recipe)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(
                    {
                        isFavorite.value = false
                        state.value = UiState("success", "Removed recipe from favorites")
                    },
                    {
                        state.value = UiState(it.cause.toString(), it.message!!)
                    })
        )
    }

    fun addFavorite(recipe: Recipe){
        composite.add(
            dbRepository.getFavoritesCount()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(
                    {
                        if (it >= 10) state.value = UiState("error", "Favorites limit reached")
                        else insertFavorite(recipe)
                    },
                    {state.value = UiState(it.cause.toString(), it.message!!)})
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
                        state.value = UiState("success", "Added recipe to favorites")
                    },
                    {state.value = UiState(it.cause.toString(), it.message!!)})
        )
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }

}