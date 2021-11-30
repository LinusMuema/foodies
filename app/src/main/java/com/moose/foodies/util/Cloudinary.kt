package com.moose.foodies.util

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.moose.foodies.FoodiesApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton


sealed class UploadState {
    object Idle: UploadState()
    data class Success(val url: String) : UploadState()
    data class Error(val message: String?) : UploadState()
    data class Loading(val current: Long, val total: Long) : UploadState()
}


@Module
@InstallIn(SingletonComponent::class)
object CloudinaryModule {

    @Provides
    @Singleton
    fun provideManager(): MediaManager {
        MediaManager.init(FoodiesApplication.appContext)
        return MediaManager.get()
    }

    @Provides
    @Singleton
    fun providesCloudinary(manager: MediaManager): Cloudinary = Cloudinary(manager)
}


class Cloudinary @Inject constructor(private val manager: MediaManager) {

    private val _progress: MutableLiveData<UploadState> = MutableLiveData()
    val progress: LiveData<UploadState> = _progress

    fun clearProgress()  = UploadState.Idle.also { _progress.value = it }

    fun uploadImage(dir: String, path: Uri) {
        val callback = object : UploadCallback {
            override fun onStart(requestId: String?) {
                _progress.value = UploadState.Loading(1, 100)
            }

            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                _progress.value = UploadState.Loading(bytes, totalBytes)
            }

            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                val url = resultData!!["secure_url"].toString()
                _progress.value = UploadState.Success(url)
            }

            override fun onError(requestId: String?, error: ErrorInfo?) {
                val message = error!!.description
                _progress.value = UploadState.Error(message)
            }

            override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
        }

        manager.upload(path)
            .option("public_id", dir)
            .callback(callback)
            .dispatch()
    }
}