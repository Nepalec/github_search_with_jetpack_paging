package com.example.githubsearchapp.repo

import android.content.Context
import com.example.githubsearchapp.network.GithubApi
import javax.inject.Inject

class DataProvider @Inject constructor(private val api: GithubApi, private val context: Context)
{

}