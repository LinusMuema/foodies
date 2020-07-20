package com.moose.foodies.features.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moose.foodies.models.RecipeResults
import com.moose.foodies.models.UiState
import com.moose.foodies.models.Video
import com.moose.foodies.network.ApiRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val apiRepository: ApiRepository): ViewModel() {
    private val composite = CompositeDisposable()
    var text = "Hello there"
    var recipes: MutableLiveData<List<RecipeResults>> = MutableLiveData()
    var videos: MutableLiveData<List<Video>> = MutableLiveData()
    var state: MutableLiveData<UiState> = MutableLiveData()

    fun searchRecipe(name: String) {
        composite.add(
            apiRepository.searchRecipes(name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    recipes.value = it.recipes
                    videos.value = it.videos
                },
                    {state.value = UiState(it.cause.toString(), it.message) })
        )
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }
}