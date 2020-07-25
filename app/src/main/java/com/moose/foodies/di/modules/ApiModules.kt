package com.moose.foodies.di.modules

import com.moose.foodies.di.network.ApiEndpoints
import com.moose.foodies.util.NetworkProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModules {

    @Singleton
    @Provides
    fun provideClient() = NetworkProvider.provideClient()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = NetworkProvider.provideRetrofit(okHttpClient)

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): ApiEndpoints {
        return retrofit.create(ApiEndpoints::class.java)
    }
}