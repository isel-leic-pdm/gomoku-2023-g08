package Tests

import Gomoku.Rankings.RankingScreen
import Gomoku.State.IdleUserRank
import Gomoku.State.LoadStateUserRank
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class RankingTests {

    @get:Rule
    val composeTestRule = createComposeRule()
    @Test
    fun test_renders_screen_with_buttons_and_search_ui() {
        // Arrange
        val onBackRequested: () -> Unit = { }
        val onFetchRankingRequest: () -> Unit = { }
        val onUserSearchRequest: (String) -> Unit = { }
        val rank: LoadStateUserRank = IdleUserRank

        // Act
        composeTestRule.setContent {
            RankingScreen(
                onBackRequested = onBackRequested,
                onFetchRankingRequest = onFetchRankingRequest,
                onUserSearchRequest = onUserSearchRequest,
                rank = rank
            )
        }

        // Assert
        composeTestRule.onNodeWithText("Back").assertIsDisplayed()
        composeTestRule.onNodeWithText("Fetch Ranking").assertIsDisplayed()
        composeTestRule.onNodeWithTag("FetchRankingButton").assertIsDisplayed()
    }
}