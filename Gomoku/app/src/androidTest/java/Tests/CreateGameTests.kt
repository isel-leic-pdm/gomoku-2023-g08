package Tests

import Gomoku.NewGame.NewGameScreen
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class CreateGameTests {

    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun should_render_new_game_screen_with_buttons() {
        // Arrange
        val onBackRequested: () -> Unit = {}
        val onFetchCreateGameRequest: () -> Unit = {}
        var selectedVariante = ""
        var selectedOpeningRule = ""

        // Act
        composeTestRule.setContent {
            NewGameScreen(
                onBackRequested = onBackRequested,
                onFetchCreateGameRequest = onFetchCreateGameRequest,
                setOpeningRule = { selectedOpeningRule = it },
                setVariante = { selectedVariante = it }
            )
        }

        // Assert
        composeTestRule.onNodeWithTag("OMOK").assertIsDisplayed()
        composeTestRule.onNodeWithTag("NORMAL").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PRO").assertIsDisplayed()
        composeTestRule.onNodeWithTag("NORMALL").assertIsDisplayed()
        composeTestRule.onNodeWithTag("CreateGameButton").assertIsDisplayed()
    }
}