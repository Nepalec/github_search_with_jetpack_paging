package com.example.githubsearchapp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubsearchapp.repo.DataProvider
import javax.inject.Inject

class AppVMFactory @Inject constructor(dp: DataProvider): ViewModelProvider.Factory {

    private val dataProvider = dp

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass == SearchViewModel::class.java)
            return SearchViewModel(dataProvider) as T
        else
            throw IllegalArgumentException("ViewModel Not Found")

    }
}