package com.moose.foodies.features.feature_favorites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.moose.foodies.FoodiesApplication
import com.moose.foodies.features.feature_favorites.data.FavoritesRepository
import com.moose.foodies.features.feature_favorites.work.BackupWorker
import com.moose.foodies.features.feature_home.domain.Recipe
import com.moose.foodies.util.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(private val repository: FavoritesRepository): ViewModel() {
    private val composite = CompositeDisposable()

    private val _favorites = MutableLiveData<Result<List<Recipe>>>()
    val favorites: LiveData<Result<List<Recipe>>> = _favorites

    fun getFavorites(){
        composite.add(
            repository.getFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {_favorites.postValue(Result.Success(it))},
                    {_favorites.postValue(Result.Error(it.message))}
                )
        )
    }

    fun removeFavorite(id: Int){
        composite.add(
            repository.deleteFavorite(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }

    fun startBackup() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val work = OneTimeWorkRequestBuilder<BackupWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(FoodiesApplication.getInstance()).enqueue(work)
    }
}