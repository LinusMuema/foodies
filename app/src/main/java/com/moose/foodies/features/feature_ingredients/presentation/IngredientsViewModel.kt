package com.moose.foodies.features.feature_ingredients.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moose.foodies.features.feature_ingredients.data.IngredientsRepository
import com.moose.foodies.features.feature_ingredients.domain.Recipe
import com.moose.foodies.util.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class IngredientsViewModel @Inject constructor(private val repository: IngredientsRepository): ViewModel() {
    private val composite = CompositeDisposable()

    private val _recipes = MutableLiveData<Result<List<Recipe>>>()
    val recipes: LiveData<Result<List<Recipe>>> = _recipes

    fun getRecipes(ingredients: String) {
        composite.add(
            repository.getRecipes(ingredients)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {_recipes.postValue(Result.Success(it))},
                    {_recipes.postValue(Result.Error(it.message))}
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }
}