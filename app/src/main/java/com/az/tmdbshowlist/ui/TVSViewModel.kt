package com.az.tmdbshowlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.az.tmdbshowlist.data.TVSRepository
import com.az.tmdbshowlist.ui.ShowListState.Companion.NETWORKING_ERROR_CODE
import com.az.tmdbshowlist.ui.ShowListState.Companion.NO_DATA_ERROR_CODE
import com.az.tmdbshowlist.ui.util.SingleUseEvent
import kotlinx.coroutines.launch
import retrofit2.HttpException

class TVSViewModel(private val tvsRepository: TVSRepository) : ViewModel() {

    private var _tvShowListState: MutableLiveData<ShowListState> = MutableLiveData()
    val tvShowListState: LiveData<ShowListState>
        get() = _tvShowListState

    private var _showSortButton: MutableLiveData<SingleUseEvent<Boolean>> = MutableLiveData()
    val showSortButton: LiveData<SingleUseEvent<Boolean>>
        get() = _showSortButton

    fun fetchTVShows() = viewModelScope.launch {
        try {
            val showList = tvsRepository.fetchTVShows()

            if (showList.isNotEmpty()) {
                _tvShowListState.value = ShowListState.Success(showList)
            } else {
                _tvShowListState.value = ShowListState.Error(NO_DATA_ERROR_CODE)
            }
        } catch (networkException: HttpException) {
            _tvShowListState.value = ShowListState.Error(NETWORKING_ERROR_CODE)
        }

        _showSortButton.value = SingleUseEvent(true)
    }

    fun sortTVShowsAlphabetically() =
        _tvShowListState.value?.let { currentTvShowList ->
            val tvShowsByName =
                (currentTvShowList as ShowListState.Success).moviesList.sortedBy { it.showName }
            _tvShowListState.value = ShowListState.Success(tvShowsByName)
            _showSortButton.value = SingleUseEvent(false)
        }

}