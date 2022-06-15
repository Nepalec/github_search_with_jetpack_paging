package com.example.githubsearchapp.network.result

interface HttpResponse {

    val statusCode: Int

    val statusMessage: String?

    val url: String?
}
