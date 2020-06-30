package com.moose.foodies.util

import com.moose.foodies.network.Authenticator
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetworkProvider {
    private const val baseUrl = "https://foodies-db.herokuapp.com/"

    private fun provideRxAdapter(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

    private fun provideLoggingInterceptor() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private fun provideAuthenticator() = Authenticator()

    fun provideClient() = OkHttpClient.Builder()
        .addInterceptor(provideAuthenticator())
        .addInterceptor(provideLoggingInterceptor())
        .build()

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addCallAdapterFactory(provideRxAdapter())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}