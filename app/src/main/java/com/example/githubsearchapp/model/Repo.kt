package com.example.githubsearchapp.model

import com.google.gson.annotations.SerializedName


data class Repo(
    val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("description") val description: String?,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("url") val apiUrl: String,
    @SerializedName("stargazers_count") val stars: Int,
    @SerializedName("subscribers_count") val watchers: Int?,
    @SerializedName("forks_count") val forks: Int?,
    @SerializedName("language") val language: String?,
    @SerializedName("homepage") val homepage: String?

)