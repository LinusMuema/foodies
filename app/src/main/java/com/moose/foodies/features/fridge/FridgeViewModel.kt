package com.moose.foodies.features.fridge

import androidx.lifecycle.MutableLiveData
import com.moose.foodies.di.network.ApiRepository
import com.moose.foodies.features.BaseViewModel
import com.moose.foodies.models.FridgeRecipe
import com.moose.foodies.util.ExceptionParser
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FridgeViewModel @Inject constructor(private val apiRepository: ApiRepository): BaseViewModel() {

    val instructions: MutableLiveData<FridgeRecipe> = MutableLiveData()

    fun getRecipes(ingredients: String) {
        composite.add(
            apiRepository.searchFridgeRecipes(ingredients)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response.value = it },
                    { exception.value = ExceptionParser.parse(it)}))
    }

    fun getRecipeById(id: String){
        composite.add(
            apiRepository.getRecipeById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {instructions.value = it},
                    {exception.value = ExceptionParser.parse(it)})
        )
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }
}