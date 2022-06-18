package com.example.githubsearchapp.model

data class Profile(
    val avatar_url: String,
    val bio: String?,
    val login: String,
    val followers: Int,
    val twitter_username: String?
)