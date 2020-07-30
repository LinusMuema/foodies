package com.moose.foodies.features.intolerances

import androidx.lifecycle.MutableLiveData
import com.moose.foodies.di.network.ApiRepository
import com.moose.foodies.features.BaseViewModel
import com.moose.foodies.models.Intolerance
import com.moose.foodies.models.Intolerances
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class IntolerancesViewModel @Inject constructor(private val apiRepository: ApiRepository): BaseViewModel() {
    private val intolerances: ArrayList<Intolerance> = ArrayList()
    val saveResult: MutableLiveData<Boolean> = MutableLiveData()

    fun getIntolerances() {
        composite.add(
            apiRepository.getIntolerances()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response.value = it.intolerances },
                    {exception.value = it.message})
        )
    }

    fun update(){
        composite.add(
            apiRepository.updateIntolerances(Intolerances(intolerances, "posting"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { saveResult.value = it.updated },
                    {exception.value = it.message})
        )
    }
    fun handleItem(intolerance: Intolerance, selected: Boolean) {
        if (selected) intolerances.add(intolerance)
        else intolerances.remove(intolerance)
    }

}