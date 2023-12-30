package Tests

import Gomoku.Main.MainScreen
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class MainScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_renders_main_screen() {
        // Arrange

        // Act
        composeTestRule.setContent {
            MainScreen()
        }

        // Assert
        composeTestRule.onNodeWithTag("gomoku_logo_tag").assertIsDisplayed()
        composeTestRule.onNodeWithText("Create User").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login & Play").assertIsDisplayed()
        composeTestRule.onNodeWithText("About").assertIsDisplayed()
    }

    @Test
    fun test_renders_main_screen_with_image() {
        // Arrange

        // Act
        composeTestRule.setContent {
            MainScreen()
        }

        // Assert
        composeTestRule.onNodeWithTag("gomoku_logo_tag").assertIsDisplayed()
    }
}