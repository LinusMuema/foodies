package com.moose.foodies.features

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.moose.foodies.FoodiesApplication
import com.moose.foodies.backup.FavoritesBackupWorker
import com.moose.foodies.db.DbRepository
import com.moose.foodies.network.ApiRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class BaseViewModel @Inject constructor(): ViewModel() {
    @Inject lateinit var dbRepository: DbRepository
    @Inject lateinit var apiRepository: ApiRepository

    val composite = CompositeDisposable()
    val exception: MutableLiveData<String> = MutableLiveData()
    val response = MutableLiveData<Any>()

    private val workManager = WorkManager.getInstance(FoodiesApplication.getInstance())
    private val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).setRequiresBatteryNotLow(true).build()
    val work = OneTimeWorkRequest.Builder(FavoritesBackupWorker::class.java).setConstraints(constraints).build()

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }

    fun startBackup() {
        workManager.enqueue(work)
    }

    fun getBackup() {
        composite.add(
            apiRepository.getBackedUpRecipes()
                .subscribe(
                    { it ->
                        dbRepository.insertFavorites(it)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.computation())
                            .subscribe(
                                {exception.value = "Retrieved backed up favorites"},
                                {exception.value = it.message})
                    },
                    {exception.value = it.message})
        )
    }
}
