package com.moose.foodies.features

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.moose.foodies.backup.FavoritesBackupWorker
import com.moose.foodies.db.DbRepository
import com.moose.foodies.di.network.ApiRepository
import com.moose.foodies.util.PreferenceHelper
import io.reactivex.disposables.CompositeDisposable
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
            val work = OneTimeWorkRequest.Builder(FavoritesBackupWorker::class.java)
                .setConstraints(constraints)
                .build()
            WorkManager.getInstance(context).enqueue(work).result
        }
    }

    fun getBackup() {
        
    }
}
