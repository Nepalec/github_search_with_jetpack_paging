package com.example.githubsearchapp.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubsearchapp.model.Profile
import com.example.githubsearchapp.model.Repo
import com.example.githubsearchapp.network.GithubApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
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

    fun getProfile(l: String): LiveData<Profile?> = liveData(Dispatchers.IO) {
        val resp = api.getProfile(l)
        if(resp.isSuccessful)  emit(resp.body()!!.toProfile())
        else emit(null)
    }


    companion object {
        const val PAGE_SIZE = 50
    }
}