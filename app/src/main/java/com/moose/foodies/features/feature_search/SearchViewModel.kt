package com.moose.foodies.features.feature_search

import androidx.lifecycle.MutableLiveData
import com.moose.foodies.features.BaseViewModel
import com.moose.foodies.models.RecipeResults
import com.moose.foodies.models.Video
import com.moose.foodies.util.ExceptionParser
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class SearchViewModel @Inject constructor(): BaseViewModel() {
    var recipes: MutableLiveData<List<RecipeResults>> = MutableLiveData()
    var videos: MutableLiveData<List<Video>> = MutableLiveData()
    var loadState: MutableLiveData<Boolean> = MutableLiveData()

    fun searchRecipe(name: String) {
        composite.add(
            apiRepository.searchRecipes(name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    recipes.value = it.recipes
                    videos.value = it.videos
                },
                    {exception.value = ExceptionParser.parse(it)})
        )
    }
}