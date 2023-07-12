package com.az.tmdbshowlist.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.az.tmdbshowlist.data.TVSRepository
import com.az.tmdbshowlist.data.remote.model.TVShowBody
import com.az.tmdbshowlist.ui.ShowListState.Companion.NETWORKING_ERROR_CODE
import com.az.tmdbshowlist.ui.ShowListState.Companion.NO_DATA_ERROR_CODE
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
        val expectedShowId = 1
        val expectedShowName = "Test Show 1"

        val mockList = listOf<TVShowBody>(
            mockk(relaxed = true) {
                every { id } returns expectedShowId
                every { name } returns expectedShowName
            }
        )
        coEvery { tvsRepository.fetchTVShows() } returns mockList

        // When
        viewModel.fetchTVShows()

        // Then
        val tvShowUICapturingSlot = CapturingSlot<ShowListState>()
        verify(exactly = 1) { showListStateObserver.onChanged(capture(tvShowUICapturingSlot)) }

        val capturedShowsList = (tvShowUICapturingSlot.captured as ShowListState.Success).moviesList
        assert(capturedShowsList[0].showId == expectedShowId)
        assert(capturedShowsList[0].showName == expectedShowName)
    }

    @Test
    fun `Given TVShows where previously fetched When sorting alphabetically Then data is sorted as expected`() {
        // Given
        val firstExpectedId = 1
        val secondExpectedId = 2
        val firstExpectedShowName = "B Test Show 1"
        val secondExpectedShowName = "A Test Show 1"

        val unsortedList = listOf<TVShowBody>(
            mockk(relaxed = true) {
                every { id } returns firstExpectedId
                every { name } returns firstExpectedShowName
            },
            mockk(relaxed = true) {
                every { id } returns secondExpectedId
                every { name } returns secondExpectedShowName
            }
        )
        coEvery { tvsRepository.fetchTVShows() } returns unsortedList

        // When
        viewModel.fetchTVShows()

        // And
        viewModel.sortTVShowsAlphabetically()

        // Then
        val sortedList = unsortedList.sortedBy { it.name }

        val firstTvShowUICapturingSlot = CapturingSlot<ShowListState>()
        val secondTvShowUICapturingSlot = CapturingSlot<ShowListState>()
        verifySequence {
            showListStateObserver.onChanged(capture(firstTvShowUICapturingSlot))
            showListStateObserver.onChanged(capture(secondTvShowUICapturingSlot))
        }

        val firstCapturedList =
            (firstTvShowUICapturingSlot.captured as ShowListState.Success).moviesList
        val secondCapturedList =
            (secondTvShowUICapturingSlot.captured as ShowListState.Success).moviesList

        assert(firstCapturedList[0].showId == unsortedList[0].id)
        assert(secondCapturedList[0].showId == sortedList[0].id)
    }

    @Test
    fun `Given show list is sorted Then the sort button event is observed as expected`() {
        // Given
        val mockList = listOf<TVShowBody>(
            mockk(relaxed = true) {
                every { id } returns 1
                every { name } returns "B Test Show 1"
            },
            mockk(relaxed = true) {
                every { id } returns 2
                every { name } returns "A Test Show 1"
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
        val mockList: List<TVShowBody> = emptyList()
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