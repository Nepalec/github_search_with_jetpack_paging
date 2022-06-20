package com.example.githubsearchapp.repo


import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubsearchapp.model.Repo
import com.example.githubsearchapp.network.GithubApi
import com.example.githubsearchapp.network.dto.ProfileDTO
import com.example.githubsearchapp.network.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataProvider @Inject constructor(private val api: GithubApi)
{
     fun getPagedRepos(searchBy: String): Flow<PagingData<Repo>>
     {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ReposPagingSource(api, PAGE_SIZE, searchBy) }
        ).flow
    }

    fun getProfile(l: String): LiveData<Result<ProfileDTO>> = liveData(Dispatchers.IO) {
        emit(api.getProfile(l))
    }


    companion object {
        const val PAGE_SIZE = 50
    }
}