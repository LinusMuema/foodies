package com.moose.foodies.di

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.moose.foodies.FoodiesApplication
import com.moose.foodies.di.factory.RxWorkerFactory
import com.moose.foodies.di.modules.*
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivitiesModule::class,
    FragmentsModule::class,
    DatabaseModules::class,
    ViewModelModules::class,
    AppAssistedInjectModule::class,
    WorkerModules::class,
    ApiModules::class])
interface AppComponent{

    fun workFactory(): RxWorkerFactory

    fun inject(app: FoodiesApplication)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }
}

@Module(includes = [AssistedInject_AppAssistedInjectModule::class])
@AssistedModule
abstract class AppAssistedInjectModule {}

interface WorkerFactory {
    fun create(context: Context, params: WorkerParameters): ListenableWorker
}