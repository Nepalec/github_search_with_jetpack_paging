package com.example.githubsearchapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Ivan's test app\n\n v1.0"
    }
    val text: LiveData<String> = _text
}