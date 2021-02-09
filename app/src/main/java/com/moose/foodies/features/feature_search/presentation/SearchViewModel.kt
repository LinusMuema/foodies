package com.moose.foodies.features.feature_search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moose.foodies.features.feature_search.data.SearchRepository
import com.moose.foodies.features.feature_search.domain.Recipe
import com.moose.foodies.features.feature_search.domain.Video
import com.moose.foodies.util.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: SearchRepository): ViewModel() {

    private val composite = CompositeDisposable()

    private val _recipes = MutableLiveData<Result<List<Recipe>>>()
    val recipes: LiveData<Result<List<Recipe>>> = _recipes

    private val _videos = MutableLiveData<Result<List<Video>>>()
    val videos: LiveData<Result<List<Video>>> = _videos

    fun searchRecipe(query: String) {
        composite.add(
            repository.searchRecipe(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _recipes.postValue(Result.Success(it.recipes))
                        _videos.postValue(Result.Success(it.videos))
                    },
                    {
                        _recipes.postValue(Result.Error(it.message))
                        _videos.postValue(Result.Error(it.message))
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }
}