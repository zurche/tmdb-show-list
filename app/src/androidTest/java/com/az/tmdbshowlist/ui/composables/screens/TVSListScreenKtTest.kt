package com.az.tmdbshowlist.ui.composables.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.lifecycle.MutableLiveData
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
                TVSListScreen(showSortButton = mockShowSortButtonState)
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
}