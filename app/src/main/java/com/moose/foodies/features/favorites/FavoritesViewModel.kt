package com.moose.foodies.features.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moose.foodies.db.DbRepository
import com.moose.foodies.models.Recipe
import com.moose.foodies.models.UiState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(private val dbRepository: DbRepository): ViewModel() {
    private val composite = CompositeDisposable()
    val favorites: MutableLiveData<List<Recipe>> = MutableLiveData()
    val state: MutableLiveData<UiState> = MutableLiveData()

    fun getFavorites(){
        composite.add(
            dbRepository.getFavorites()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(
                    {
                        if (it.isEmpty()) state.value = UiState("exception", "no favorites available")
                        else favorites.value = it
                    },
                    {state.value = UiState(it.cause.toString(), it.message!!) })
        )
    }

    fun removeFavorite(recipe: Recipe){
        composite.add(
            dbRepository.deleteFavorite(recipe)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(
                    { return@subscribe },
                    {state.value = UiState(it.cause.toString(), it.message!!) })
        )
    }

    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }
}