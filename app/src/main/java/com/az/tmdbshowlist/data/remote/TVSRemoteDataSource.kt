package com.az.tmdbshowlist.data.remote

import com.az.tmdbshowlist.data.remote.model.TVShowBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TVSRemoteDataSource(private val api: TVSApi,
                          private val apiKey: String) {
    suspend fun getFirstPageOfTVShows(): List<TVShowBody> =
        withContext(Dispatchers.IO) {
            api.getTopRatedTVShows(apiKey = apiKey, page = 1)
                .results
        }
}