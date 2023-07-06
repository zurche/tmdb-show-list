package com.az.tmdbshowlist.ui.composables

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import com.az.tmdbshowlist.ui.composables.components.TVSFloatingActionButton
import com.az.tmdbshowlist.ui.composables.components.TVShowUIListItem
import com.az.tmdbshowlist.ui.model.TVShowUI

val mockData = MutableLiveData(
    listOf(
        TVShowUI(
            1,
            "Breaking Bad",
            ""
        )
    )
)

@Composable
@Preview
fun TVSListScreen(
    tvShowList: LiveData<List<TVShowUI>> = mockData,
    onSortClicked: () -> Unit = {}
) {
    val tvShows = tvShowList.observeAsState(initial = emptyList())

    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
        items(count = tvShows.value.size, itemContent = { index ->
            TVShowUIListItem(tvShow = tvShows.value[index])
        })
    })

    TVSFloatingActionButton(onSortClicked)
}