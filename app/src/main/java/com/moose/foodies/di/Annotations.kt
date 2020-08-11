package com.moose.foodies.di

import androidx.lifecycle.ViewModel
import androidx.work.RxWorker
import dagger.MapKey
import javax.inject.Scope
import kotlin.reflect.KClass


// Annotation for having custom keys for viewmodel Factory map
@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ViewModelKey(val value: KClass<out ViewModel>)

// Annotation for creating instances of worker classes
@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class WorkerKey(val value: KClass<out RxWorker>)

// Scopes dependencies injected into activity to be preserved as long as the activity component is still alive
@Scope
annotation class ActivityScope