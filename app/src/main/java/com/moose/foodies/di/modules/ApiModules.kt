package com.moose.foodies.di.modules

import com.moose.foodies.network.ApiEndpoints
import com.moose.foodies.network.Authenticator
import com.moose.foodies.util.NetworkProvider
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
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