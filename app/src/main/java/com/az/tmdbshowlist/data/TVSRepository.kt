package com.az.tmdbshowlist.data

import com.az.tmdbshowlist.data.remote.TVSRemoteDataSource
import com.az.tmdbshowlist.data.remote.model.TVShowBody
import com.az.tmdbshowlist.ui.model.TVShowUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TVSRepository(private val remoteDataSource: TVSRemoteDataSource) {
    suspend fun fetchTVShows(): List<TVShowBody> =
        withContext(Dispatchers.IO) {
            remoteDataSource.getFirstPageOfTVShows()
        }
}