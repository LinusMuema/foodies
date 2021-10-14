package com.moose.foodies.util

import android.app.Activity
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.github.dhaval2404.imagepicker.ImagePicker
import com.moose.foodies.FoodiesApplication
import com.moose.foodies.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


sealed class UploadState {
    object Idle : UploadState()
    data class Success(val url: String) : UploadState()
    data class Loading(val percentage: Long) : UploadState()
    data class Error(val message: String?) : UploadState()
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
    fun providesCloudinary(dao: UserDao, manager: MediaManager): Cloudinary {
        return Cloudinary(dao, manager)
    }
}


class Cloudinary @Inject constructor(private val dao: UserDao, private val manager: MediaManager) {

    private val _progress: MutableLiveData<UploadState> = MutableLiveData()
    val progress: LiveData<UploadState> = _progress

    suspend fun uploadImage(path: Uri) {
        val name = RandomIdGenerator.getRandom()
        val user = dao.getProfile().first()._id
        val callback = object : UploadCallback {
            override fun onStart(requestId: String?) {
                _progress.value = UploadState.Loading(0)
            }

            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                val percentage = (bytes / totalBytes) * 100
                _progress.value = UploadState.Loading(percentage)
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
            .option("public_id", "Foodies/recipes/$user/$name")
            .callback(callback)
            .dispatch()
    }

    suspend fun deleteImage() {

    }

}