package com.moose.foodies.di.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

class DaggerWorkerFactory @Inject constructor(
    private val factories: Map<Class <out ListenableWorker>, @JvmSuppressWildcards Provider<ChildWorkerFactory>>
): WorkerFactory() {

    override fun createWorker(context: Context, className: String, params: WorkerParameters): ListenableWorker? {
        val worker = factories.entries.find { Class.forName(className).isAssignableFrom(it.key) }
        return worker?.value?.get()?.create(context, params)
    }

    interface ChildWorkerFactory {
        fun create(context: Context, parameters: WorkerParameters): ListenableWorker
    }
}