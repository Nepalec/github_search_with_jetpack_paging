package com.example.githubsearchapp

import android.app.Application
import com.example.githubsearchapp.di.AppComponent
import com.example.githubsearchapp.di.DaggerAppComponent

class MyApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }
}