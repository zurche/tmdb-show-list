package com.az.tmdbshowlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.az.tmdbshowlist.data.TVSRepository
import com.az.tmdbshowlist.ui.model.TVShowUI

class TVSViewModel(private val tvsRepository: TVSRepository) : ViewModel() {

    private var _tvShowList: MutableLiveData<List<TVShowUI>> = MutableLiveData()
    val tvShowList: LiveData<List<TVShowUI>>
        get() = _tvShowList

    fun fetchTVShows() {
        _tvShowList.value = tvsRepository.fetchTVShows()
    }

}