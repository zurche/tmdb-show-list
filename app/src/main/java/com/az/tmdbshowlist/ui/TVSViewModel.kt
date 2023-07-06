package com.az.tmdbshowlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.az.tmdbshowlist.data.TVSRepository
import com.az.tmdbshowlist.ui.model.TVShowUI
import com.az.tmdbshowlist.ui.util.SingleUseEvent

class TVSViewModel(private val tvsRepository: TVSRepository) : ViewModel() {

    private var _tvShowList: MutableLiveData<List<TVShowUI>> = MutableLiveData()
    val tvShowList: LiveData<List<TVShowUI>>
        get() = _tvShowList

    private var _showSortButton: MutableLiveData<SingleUseEvent<Boolean>> = MutableLiveData()
    val showSortButton: LiveData<SingleUseEvent<Boolean>>
        get() = _showSortButton

    fun fetchTVShows() {
        _tvShowList.value = tvsRepository.fetchTVShows()
        _showSortButton.value = SingleUseEvent(true)
    }

    fun sortTVShowsAlphabetically() =
        _tvShowList.value?.let { currentTvShowList ->
            val tvShowsByName = currentTvShowList.sortedBy { it.showName }
            _tvShowList.value = tvShowsByName
            _showSortButton.value = SingleUseEvent(false)
        }

}