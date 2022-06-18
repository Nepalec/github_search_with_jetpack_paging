package com.example.githubsearchapp.di

//import kotlinx.serialization.json.Json
import android.content.Context
import com.example.githubsearchapp.network.BASE_URL
import com.example.githubsearchapp.network.GithubApi
import com.example.githubsearchapp.network.result.ResultAdapterFactory
import com.example.githubsearchapp.repo.DataProvider
import com.example.githubsearchapp.vm.AppVMFactory
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .addCallAdapterFactory(ResultAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttp)
            .build()
            .create(GithubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDataP(api:GithubApi) = DataProvider(api)

    @Provides
    @Singleton
    fun provideViewModelFactory(dataProvider: DataProvider) = AppVMFactory(dataProvider)

}
