package com.example.githubsearchapp.network.dto

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.githubsearchapp.model.Repo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Serializable
data class RepoDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("updated_at") val updated_at: String?,
    @SerializedName("owner") val owner: OwnerDTO,
    @SerializedName("stargazers_count") val stars: Int

//    @SerializedName("html_url") val htmlUrl: String,
//    @SerializedName("url") val apiUrl: String,
//    @SerializedName("subscribers_count") val watchers: Int?,
//    @SerializedName("forks_count") val forks: Int?,
//    @SerializedName("homepage") val homepage: String?,


)
{

    fun toRepo(): Repo = Repo(
        id = id,
        name = name,
        description = description,
        stars = stars,
        language = language,
        owner = owner,
        updated_at = convertDT(updated_at),
        counter=0

    )

    private fun convertDT(updatedAt: String?):String?  = try {
            LocalDate.parse(updatedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        } catch (e: Exception) {
            null
        }


}