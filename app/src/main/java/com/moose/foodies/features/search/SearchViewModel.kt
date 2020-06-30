package com.moose.foodies.features.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moose.foodies.models.FridgeSearch
import com.moose.foodies.models.RecipeSearch
import com.moose.foodies.models.UiState
import com.moose.foodies.network.ApiRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val apiRepository: ApiRepository): ViewModel() {
    private val composite = CompositeDisposable()
    var searchResults: MutableLiveData<RecipeSearch> = MutableLiveData()
    var ingredientsResult: MutableLiveData<FridgeSearch> = MutableLiveData()
    var state: MutableLiveData<UiState> = MutableLiveData()

    fun searchRecipe(name: String) {
        composite.add(
            apiRepository.searchRecipes(name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({searchResults.value = it},
                    {state.value = UiState(it.cause.toString(), it.message) })
        )
    }

    fun searchRecipeByIngredients(ingredients: String) {
        composite.add(
            apiRepository.searchRecipeByIngredients(ingredients)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ingredientsResult.value = it},
                    {state.value = UiState(it.cause.toString(), it.message) })
        )
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }
}