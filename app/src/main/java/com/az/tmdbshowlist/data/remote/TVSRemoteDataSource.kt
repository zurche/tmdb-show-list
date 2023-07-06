package com.az.tmdbshowlist.data.remote

import com.az.tmdbshowlist.data.remote.model.toTVShowUI
import com.az.tmdbshowlist.ui.model.TVShowUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TVSRemoteDataSource(private val api: TVSApi,
                          private val apiKey: String) {
    suspend fun getFirstPageOfTVShows(): List<TVShowUI> =
        withContext(Dispatchers.IO) {
            api.getTopRatedTVShows(apiKey = apiKey, page = 1)
                .results.map {
                    it.toTVShowUI()
                }
        }
}