package com.example.githubsearchapp.di

import android.content.Context
import com.example.githubsearchapp.network.BASE_URL
import com.example.githubsearchapp.network.GithubApi
import com.example.githubsearchapp.network.result.ResultAdapterFactory
import com.example.githubsearchapp.repo.DataProvider
import com.example.githubsearchapp.vm.AppVMFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideOkHttp() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
        .connectTimeout(0, TimeUnit.MILLISECONDS)
        .callTimeout(0, TimeUnit.MILLISECONDS)
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(true)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttp: OkHttpClient): GithubApi
    {
        val json = Json {
            ignoreUnknownKeys = true
        }

        return Retrofit.Builder()
            .addCallAdapterFactory(ResultAdapterFactory())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .client(okHttp)
            .build()
            .create(GithubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDataP(api:GithubApi, ctx:Context) = DataProvider(api, ctx)

    @Provides
    @Singleton
    fun provideViewModelFactory(dataProvider: DataProvider) = AppVMFactory(dataProvider)

}
