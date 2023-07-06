package com.az.tmdbshowlist.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.az.tmdbshowlist.data.TVSRepository
import com.az.tmdbshowlist.ui.model.TVShowUI
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Rule
import org.junit.Test


internal class TVSViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TVSViewModel
    private lateinit var tvsRepository: TVSRepository
    private lateinit var tvShowObserver: Observer<List<TVShowUI>>

    @Before
    fun setup() {
        tvsRepository = mockk()
        viewModel = TVSViewModel(tvsRepository)
        tvShowObserver = mockk(relaxed = true)
        viewModel.tvShowList.observeForever(tvShowObserver)
    }

    @Test
    fun `Given TVShows exist When fetching them from the ViewModel Then data is published as expected`() {
        // Given
        val mockList = listOf<TVShowUI>(
            mockk {
                every { showId } returns 1
                every { showName } returns "Test Show 1"
            }
        )
        every { tvsRepository.fetchTVShows() } returns mockList

        // When
        viewModel.fetchTVShows()

        // Then
        verify(exactly = 1) { tvShowObserver.onChanged(mockList) }
    }

    @Test
    fun `Given TVShows where previously fetched When sorting alphabetically Then data is sorted as expected`() {
        // Given
        val mockList = listOf<TVShowUI>(
            mockk {
                every { showId } returns 1
                every { showName } returns "B Test Show 1"
            },
            mockk {
                every { showId } returns 2
                every { showName } returns "A Test Show 1"
            }
        )
        every { tvsRepository.fetchTVShows() } returns mockList

        // When
        viewModel.fetchTVShows()

        // And
        viewModel.sortTVShowsAlphabetically()

        // Then
        val sortedList = mockList.sortedBy { it.showName }
        verifySequence {
            tvShowObserver.onChanged(mockList)
            tvShowObserver.onChanged(sortedList)
        }
    }
}