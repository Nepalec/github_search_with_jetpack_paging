package com.example.githubsearchapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubsearchapp.repo.DataProvider

class DetailViewModel(private val dataProvider: DataProvider) : ViewModel() {

  fun getProfile(l:String) = dataProvider.getProfile(l)
}