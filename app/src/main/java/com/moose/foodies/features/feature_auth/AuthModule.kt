package com.moose.foodies.features.feature_auth

import androidx.lifecycle.ViewModel
import com.moose.foodies.di.ActivityScope
import com.moose.foodies.di.ViewModelKey
import com.moose.foodies.features.feature_auth.data.AuthRepository
import com.moose.foodies.features.feature_auth.data.AuthRepositoryImpl
import com.moose.foodies.features.feature_auth.presentation.AuthActivity
import com.moose.foodies.features.feature_auth.presentation.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class AuthModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideAuth(): AuthActivity

    @IntoMap
    @Binds
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

    @Binds
    abstract fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}