package com.az.tmdbshowlist.data.remote

import com.az.tmdbshowlist.data.remote.model.TVShowResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TVSApi {
    @GET("tv/top_rated")
    suspend fun getTopRatedTVShows(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): TVShowResponse
}