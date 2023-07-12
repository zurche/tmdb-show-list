package com.az.tmdbshowlist.ui

import com.az.tmdbshowlist.ui.model.TVShowUI


interface ShowListState {
    data class Success(val moviesList: List<TVShowUI>) : ShowListState
    object Loading : ShowListState
    data class Error(val errorCode: Int) : ShowListState

    companion object {
        const val NETWORKING_ERROR_CODE = 1
        const val NO_DATA_ERROR_CODE = 2
    }
}