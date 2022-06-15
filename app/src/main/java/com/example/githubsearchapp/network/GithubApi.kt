package com.example.githubsearchapp.network


import com.example.githubsearchapp.model.Repo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL: String = "https://api.github.com/"
const val GET_REPOS: String = "search/repositories"


interface GithubApi {

    // Search
    @GET(GET_REPOS)
    suspend fun searchRepos(@Query("q") searchQuery: String): Result<SearchResponse>

    @GET(GET_REPOS)
    suspend fun searchRepos(@Query("q") searchQuery: String,
                            @Query("per_page") perPage: Int): Result<SearchResponse>

    @GET(GET_REPOS)
    suspend fun searchRepos(@Query("q") searchQuery: String,
                            @Query("page") pageIndex: Int,
                            @Query("per_page") perPage: Int): Result<SearchResponse>

    // Repository info
    @GET("repos/{owner}/{name}")
    suspend fun getRepo(
        @Path("owner") owner: String,   // owner login
        @Path("name") name: String      // repo name
    ): Result<Repo>


}