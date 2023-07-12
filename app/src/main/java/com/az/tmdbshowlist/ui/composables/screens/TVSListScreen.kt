package com.az.tmdbshowlist.ui.composables.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.az.tmdbshowlist.R
import com.az.tmdbshowlist.ui.ShowListState
import com.az.tmdbshowlist.ui.ShowListState.Companion.NETWORKING_ERROR_CODE
import com.az.tmdbshowlist.ui.ShowListState.Companion.NO_DATA_ERROR_CODE
import com.az.tmdbshowlist.ui.composables.components.TVSFloatingActionButton
import com.az.tmdbshowlist.ui.composables.components.TVShowUIListItem
import com.az.tmdbshowlist.ui.model.TVShowUI
import com.az.tmdbshowlist.ui.util.SingleUseEvent

val mockData = listOf(
    TVShowUI(
        1, "Breaking Bad", ""
    )
)

@Composable
fun TVSListScreen(
    tvShowListState: LiveData<ShowListState> = MutableLiveData(ShowListState.Success(mockData)),
    showSortButton: LiveData<SingleUseEvent<Boolean>> = MutableLiveData(SingleUseEvent(true)),
    onSortClicked: () -> Unit = {}
) {
    val tvShowsState = tvShowListState.observeAsState(initial = ShowListState.Loading)

    when (tvShowsState.value) {
        is ShowListState.Success -> {
            val moviesList = (tvShowsState.value as ShowListState.Success).moviesList
            TVShowList(moviesList, showSortButton, onSortClicked)
        }

        is ShowListState.Loading -> LoadingView()
        is ShowListState.Error -> {
            val errorMessage = (tvShowsState.value as ShowListState.Error).errorCode
            ErrorView(errorMessage)
        }
    }

}

@Composable
@Preview
fun ErrorView(errorCode: Int = 1) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp), contentAlignment = Alignment.Center
    ) {
        val errorMessage =
            stringResource(
                id =
                when (errorCode) {
                    NETWORKING_ERROR_CODE -> R.string.connectivity_error
                    NO_DATA_ERROR_CODE -> R.string.data_error
                    else -> R.string.something_went_wrong_error
                }
            )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Rounded.Refresh,
                contentDescription = null,
                tint = Color.White
            )
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun TVShowList(
    tvShows: List<TVShowUI>,
    showSortButton: LiveData<SingleUseEvent<Boolean>>,
    onSortClicked: () -> Unit
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
        items(count = tvShows.size, itemContent = { index ->
            TVShowUIListItem(tvShow = tvShows[index])
        })
    })

    val showSortButtonEvent = showSortButton.observeAsState(initial = SingleUseEvent(true))
    if (showSortButtonEvent.value.getContentIfNotHandled() == true) {
        TVSFloatingActionButton(onSortClicked)
    }
}