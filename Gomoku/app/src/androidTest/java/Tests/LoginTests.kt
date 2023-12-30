package Tests

import Gomoku.Login.LoginButtonBackTestTag
import Gomoku.Login.LoginButtonTestTag
import Gomoku.Login.LoginScreen
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class LoginTests {

    @get:Rule
    val composeTestRule = createComposeRule()
    @Test
    fun test_renders_screen_with_elements() {
        // Arrange

        // Act
        composeTestRule.setContent {
            LoginScreen()
        }

        // Assert
        composeTestRule.onNodeWithTag(LoginButtonBackTestTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LoginButtonTestTag).assertIsDisplayed()
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
    }
}