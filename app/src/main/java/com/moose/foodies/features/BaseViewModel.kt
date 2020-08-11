package com.moose.foodies.features

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.google.gson.Gson
import com.moose.foodies.backup.FavoritesBackupWorker
import com.moose.foodies.db.DbRepository
import com.moose.foodies.di.network.ApiRepository
import com.moose.foodies.models.Recipes
import com.moose.foodies.util.PreferenceHelper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class BaseViewModel @Inject constructor(): ViewModel() {
    @Inject lateinit var dbRepository: DbRepository
    @Inject lateinit var apiRepository: ApiRepository

    val composite = CompositeDisposable()
    val exception: MutableLiveData<String> = MutableLiveData()
    val response = MutableLiveData<Any>()

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }

    fun startBackup(context: Context) {
        Log.d("Backup", "startBackup: Starting backup")
        if (PreferenceHelper.getBackupStatus(context)){
            composite.add(
                dbRepository.getFavorites()
                    .observeOn(Schedulers.computation())
                    .subscribe(
                    {
                        val json = Gson().toJson(Recipes(0, "", it, null, null))
                        val data = workDataOf("FAVORITES_BACKUP" to json)
                        val work = OneTimeWorkRequest.Builder(FavoritesBackupWorker::class.java)
                            .setConstraints(constraints)
                            .setInputData(data)
                            .build()
                        WorkManager.getInstance(context).enqueue(work).result
                    },
                    {
                        Log.e("Backup", "startBackup: $it")
                    }
                    )
            )
        }
    }

    fun getBackup() {
        
    }
}
