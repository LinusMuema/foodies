package com.moose.foodies.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import javax.inject.Scope
import kotlin.reflect.KClass

// Annotation for having custom keys for viewmodel Factory map
@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ViewModelKey(val value: KClass<out ViewModel>)

// Scopes dependencies injected into activity to be preserved as long as the activity component is still alive
@Scope
annotation class ActivityScope