package com.az.tmdbshowlist.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.az.tmdbshowlist.data.TVSRepository
import com.az.tmdbshowlist.ui.ShowListState.Companion.NETWORKING_ERROR_CODE
import com.az.tmdbshowlist.ui.ShowListState.Companion.NO_DATA_ERROR_CODE
import com.az.tmdbshowlist.ui.model.TVShowUI
import com.az.tmdbshowlist.ui.util.SingleUseEvent
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException


internal class TVSViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TVSViewModel
    private lateinit var tvsRepository: TVSRepository
    private lateinit var showListStateObserver: Observer<ShowListState>
    private lateinit var showSortButtonObserver: Observer<SingleUseEvent<Boolean>>

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        tvsRepository = mockk()
        viewModel = TVSViewModel(tvsRepository)
        showListStateObserver = mockk(relaxed = true)
        showSortButtonObserver = mockk(relaxed = true)
        viewModel.tvShowListState.observeForever(showListStateObserver)
        viewModel.showSortButton.observeForever(showSortButtonObserver)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun cleanup() {
        Dispatchers.resetMain()
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
        coEvery { tvsRepository.fetchTVShows() } returns mockList

        // When
        viewModel.fetchTVShows()

        // Then
        verify(exactly = 1) { showListStateObserver.onChanged(ShowListState.Success(mockList)) }
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
        coEvery { tvsRepository.fetchTVShows() } returns mockList

        // When
        viewModel.fetchTVShows()

        // And
        viewModel.sortTVShowsAlphabetically()

        // Then
        val sortedList = mockList.sortedBy { it.showName }
        verifySequence {
            showListStateObserver.onChanged(ShowListState.Success(mockList))
            showListStateObserver.onChanged(ShowListState.Success(sortedList))
        }
    }

    @Test
    fun `Given show list is sorted Then the sort button event is observed as expected`() {
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
        coEvery { tvsRepository.fetchTVShows() } returns mockList

        // When
        viewModel.fetchTVShows()

        // And
        viewModel.sortTVShowsAlphabetically()

        // Then
        val showSortButtonCapturingSlotA = CapturingSlot<SingleUseEvent<Boolean>>()
        val showSortButtonCapturingSlotB = CapturingSlot<SingleUseEvent<Boolean>>()
        verifySequence {
            showSortButtonObserver.onChanged(capture(showSortButtonCapturingSlotA))
            showSortButtonObserver.onChanged(capture(showSortButtonCapturingSlotB))
        }

        assert(showSortButtonCapturingSlotA.captured.getContentIfNotHandled() == true)
        assert(showSortButtonCapturingSlotB.captured.getContentIfNotHandled() == false)
    }

    @Test
    fun `Given TVShows are empty When fetching them from the ViewModel Then error is observed`() {
        // Given
        val mockList: List<TVShowUI> = emptyList()
        coEvery { tvsRepository.fetchTVShows() } returns mockList

        // When
        viewModel.fetchTVShows()

        // Then
        verify(exactly = 1) { showListStateObserver.onChanged(ShowListState.Error(NO_DATA_ERROR_CODE)) }
    }

    @Test
    fun `Given repository layer throws HTTP Exception When checking for shows Then the expected error is observed`() {
        // Given
        coEvery { tvsRepository.fetchTVShows() } throws mockk<HttpException>()

        // When
        viewModel.fetchTVShows()

        // Then
        verify(exactly = 1) {
            showListStateObserver.onChanged(
                ShowListState.Error(
                    NETWORKING_ERROR_CODE
                )
            )
        }
    }
}