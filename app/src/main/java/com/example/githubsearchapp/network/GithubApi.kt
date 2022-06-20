package com.example.githubsearchapp.network


import com.example.githubsearchapp.network.dto.ProfileDTO
import com.example.githubsearchapp.network.result.Result

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL: String = "https://api.github.com/"

interface GithubApi {


    @GET("search/repositories")
    suspend fun searchRepos(@Query("q") searchQuery: String,
                            @Query("page") pageIndex: Int,
                            @Query("per_page") perPage: Int): Response<SearchResponse>

    // Repository info
    @GET("users/{login}")
    suspend fun getProfile( @Path("login") l: String): Result<ProfileDTO>



}