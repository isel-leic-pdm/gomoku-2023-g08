package Tests

import Gomoku.User.UserButtonTest
import Gomoku.User.UserScreen
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class UserTests {

    @get:Rule
    val composeTestRule = createComposeRule()
    @Test
    fun test_renders_screen() {
        // Arrange
        val onBackRequested: () -> Unit = {}
        val onfetchUsersRequested: () -> Unit = {}
        val onfetch: () -> Unit = {}
        val setUsername: (String) -> Unit = {}
        val setPassword: (String) -> Unit = {}

        // Act
        composeTestRule.setContent {
            UserScreen(
                onBackRequested = onBackRequested,
                onfetchUsersRequested = onfetchUsersRequested,
                onfetch = onfetch,
                SetUsername = setUsername,
                SetPassword = setPassword
            )
        }

        // Assert
        composeTestRule.onNodeWithTag(UserButtonTest).assertIsDisplayed()
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
    }
}