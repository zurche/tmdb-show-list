package com.az.tmdbshowlist.data

import com.az.tmdbshowlist.data.remote.TVSRemoteDataSource
import com.az.tmdbshowlist.ui.model.TVShowUI

class TVSRepository(private val remoteDataSource: TVSRemoteDataSource) {
    fun fetchTVShows() : List<TVShowUI> {
        return emptyList()
    }
}