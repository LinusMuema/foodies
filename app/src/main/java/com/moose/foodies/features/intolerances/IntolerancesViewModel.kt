package com.moose.foodies.features.intolerances

import androidx.lifecycle.MutableLiveData
import com.moose.foodies.features.BaseViewModel
import com.moose.foodies.models.Intolerance
import com.moose.foodies.models.Intolerances
import com.moose.foodies.util.ExceptionParser
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class IntolerancesViewModel @Inject constructor(): BaseViewModel() {
    private val intolerances: ArrayList<Intolerance> = ArrayList()
    val saveResult: MutableLiveData<Boolean> = MutableLiveData()

    fun getIntolerances() {
        composite.add(
            apiRepository.getIntolerances()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response.value = it.intolerances },
                    {exception.value = ExceptionParser.parse(it)})
        )
    }

    fun update(){
        composite.add(
            apiRepository.updateIntolerances(Intolerances(intolerances, "posting"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { saveResult.value = it.updated },
                    {exception.value = ExceptionParser.parse(it)})
        )
    }
    fun handleItem(intolerance: Intolerance, selected: Boolean) {
        if (selected) intolerances.add(intolerance)
        else intolerances.remove(intolerance)
    }

}