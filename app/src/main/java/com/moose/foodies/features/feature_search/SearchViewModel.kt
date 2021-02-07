package com.moose.foodies.features.feature_search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moose.foodies.models.RecipeResults
import com.moose.foodies.models.Video
import javax.inject.Inject

class SearchViewModel @Inject constructor(): ViewModel() {
    var recipes: MutableLiveData<List<RecipeResults>> = MutableLiveData()
    var videos: MutableLiveData<List<Video>> = MutableLiveData()
    var loadState: MutableLiveData<Boolean> = MutableLiveData()

    fun searchRecipe(name: String) {
    }
}