package com.az.tmdbshowlist.data.remote.model

import com.az.tmdbshowlist.ui.model.TVShowUI
import com.google.gson.annotations.SerializedName

data class TVShowResponse(
    val page: Int,
    val results: List<TVShowBody>,
    val totalPages: Int,
    val totalResults: Int
)

data class TVShowBody(
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    val name: String = "",
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)

fun TVShowBody.toTVShowUI() =
    TVShowUI(
        showId = id,
        showName = name,
        imagePath = "https://image.tmdb.org/t/p/w500$posterPath"
    )
