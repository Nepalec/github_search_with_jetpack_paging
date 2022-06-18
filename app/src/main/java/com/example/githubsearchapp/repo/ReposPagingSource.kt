package com.example.githubsearchapp.repo

import androidx.paging.Pager
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubsearchapp.model.Repo
import com.example.githubsearchapp.network.GithubApi
import retrofit2.HttpException

typealias ReposPageLoader = suspend (pageIndex: Int, pageSize: Int, searchBy: String) -> List<Repo>

@Suppress("UnnecessaryVariable")
class ReposPagingSource(
    private val api: GithubApi,
    private val pageSize: Int,
    private val query: String
) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {

        if (query.isBlank()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        try {
            val pageNumber = params.key ?: 0
            val response = api.searchRepos(query, pageNumber, pageSize)

            if (response.isSuccessful) {
                val repos = response.body()!!.items.mapIndexed{ ind, it -> it.toRepo().also { r->r.counter = pageNumber*DataProvider.PAGE_SIZE + ind+1} }
                val nextPageNumber = if (repos.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
                return LoadResult.Page(repos, prevPageNumber, nextPageNumber)
            } else {
                return LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        // get the most recently accessed index in the users list:
        val anchorPosition = state.anchorPosition ?: return null
        // convert item index to page index:
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        // page doesn't have 'currentKey' property, so need to calculate it manually:
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

}