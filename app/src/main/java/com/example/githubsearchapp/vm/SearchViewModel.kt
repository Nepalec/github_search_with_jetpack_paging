package com.example.githubsearchapp.vm

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubsearchapp.model.Repo
import com.example.githubsearchapp.network.dto.RepoDTO
import com.example.githubsearchapp.repo.DataProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

class SearchViewModel(private val dataProvider: DataProvider) : ViewModel() {
    val reposFlow: Flow<PagingData<Repo>>

    private val searchBy = MutableLiveData("")

    init {
        reposFlow = searchBy.asFlow()
            .debounce(500)
            .flatMapLatest {
                dataProvider.getPagedRepos(it)
            }
            .cachedIn(viewModelScope)
    }

    fun setSearchBy(value: String) {
        if (this.searchBy.value == value) return
        this.searchBy.value = value
    }

    fun refresh() {
        this.searchBy.postValue(this.searchBy.value)
    }

}
