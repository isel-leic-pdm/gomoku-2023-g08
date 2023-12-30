package Tests

import Gomoku.Landing.LandingScreen
import Gomoku.Main.MainScreen
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class LandingScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()
    @Test
    fun test_play_button_in_landing_screen() {
        // Arrange

        // Act
        composeTestRule.setContent {
            LandingScreen()
        }

        // Assert
        composeTestRule.onNodeWithText("Play").assertIsDisplayed()
    }

    @Test
    fun test_renders_landing_screen_with_image() {
        // Arrange

        // Act
        composeTestRule.setContent {
            MainScreen()
        }

        // Assert
        composeTestRule.onNodeWithTag("gomoku_logo_tag").assertIsDisplayed()
    }
}