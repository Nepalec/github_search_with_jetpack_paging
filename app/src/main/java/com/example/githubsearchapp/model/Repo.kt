package com.example.githubsearchapp.model

import com.example.githubsearchapp.network.dto.OwnerDTO
import java.time.LocalDate

data class Repo(
    val id: Long,
    val name: String,
    val description: String?,
    val language: String?,
    val updated_at: String?,
    val stars: Int,
    var counter:Int,
    val owner: OwnerDTO
)