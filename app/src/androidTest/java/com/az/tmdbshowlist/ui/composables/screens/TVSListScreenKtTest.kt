package com.az.tmdbshowlist.ui.composables.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.az.tmdbshowlist.ui.ShowListState
import com.az.tmdbshowlist.ui.ShowListState.Companion.NETWORKING_ERROR_CODE
import com.az.tmdbshowlist.ui.ShowListState.Companion.NO_DATA_ERROR_CODE
import com.az.tmdbshowlist.ui.theme.TMDBShowListTheme
import com.az.tmdbshowlist.ui.util.SingleUseEvent
import org.junit.Rule
import org.junit.Test

internal class TVSListScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun validateSortButtonIsNotPresentIfSortAlreadyHappened() {
        // Given
        val mockShowSortButtonState = MutableLiveData(SingleUseEvent(false))

        // When
        composeTestRule.setContent {
            TMDBShowListTheme {
                TVSListScreen(showSortButton = mockShowSortButtonState, )
            }
        }

        // Then
        composeTestRule.onNodeWithContentDescription("Sort List FAB").assertDoesNotExist()
    }

    @Test
    fun validateSortButtonIsPresentIfSortDidNotHappen() {
        // Given
        val mockShowSortButtonState = MutableLiveData(SingleUseEvent(true))

        // When
        composeTestRule.setContent {
            TMDBShowListTheme {
                TVSListScreen(showSortButton = mockShowSortButtonState)
            }
        }

        // Then
        composeTestRule.onNodeWithContentDescription("Sort List FAB").assertIsDisplayed()
    }

    @Test
    fun whenNetworkErrorHappensValidateConnectivityErrorIsDisplayedAsExpected() {
        // Given
        val tvShowListState = MutableLiveData(ShowListState.Error(NETWORKING_ERROR_CODE))

        // When
        composeTestRule.setContent {
            TMDBShowListTheme {
                TVSListScreen(tvShowListState = (tvShowListState as LiveData<ShowListState>) )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Connectivity Error. Please check your connection and try again").assertIsDisplayed()
    }

    @Test
    fun whenDataErrorHappensValidateConnectivityErrorIsDisplayedAsExpected() {
        // Given
        val tvShowListState = MutableLiveData(ShowListState.Error(NO_DATA_ERROR_CODE))

        // When
        composeTestRule.setContent {
            TMDBShowListTheme {
                TVSListScreen(tvShowListState = (tvShowListState as LiveData<ShowListState>) )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Shows list is empty. Try Again later.").assertIsDisplayed()
    }
}