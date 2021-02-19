package com.moose.foodies.features.feature_recipe.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moose.foodies.features.feature_home.domain.Recipe
import com.moose.foodies.features.feature_recipe.data.RecipeRepository
import com.moose.foodies.util.Result
import com.moose.foodies.util.parse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecipeViewModel @Inject constructor(private val repository: RecipeRepository): ViewModel() {

    private val composite = CompositeDisposable()

    private val _favorite = MutableLiveData<Result<Boolean>>()
    val isFavorite: LiveData<Result<Boolean>> = _favorite

    private val _recipe = MutableLiveData<Result<Recipe>>()
    val recipe: LiveData<Result<Recipe>> = _recipe


    fun getRecipe(id: Int){
        composite.add(
            repository.checkFavorite(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _recipe.postValue(Result.Success(it))
                        _favorite.postValue(Result.Success(true))
                    },
                    {
                        checkLocalRecipes(id)
                        _favorite.postValue(Result.Success(false))
                    }
                )
        )
    }

    private fun checkLocalRecipes(id: Int){
        composite.add(
            repository.getLocalRecipes(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _recipe.postValue(Result.Success(it)) },
                    { getRemoteRecipe(id) }
                )
        )
    }

    private fun getRemoteRecipe(id: Int){
        composite.add(
            repository.getRemoteRecipe(id)
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)
                .subscribe(
                    {_recipe.postValue(Result.Success(it))},
                    {_recipe.postValue(Result.Error(it.parse()))}
                )
        )
    }

    fun removeFavorite(id: Int){
        composite.add(
            repository.deleteFavorite(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _favorite.postValue(Result.Success(false)) }
        )
    }

    fun addFavorite(recipe: Recipe){
        composite.add(
            repository.addFavorite(recipe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _favorite.postValue(Result.Success(true)) }
        )
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }

}