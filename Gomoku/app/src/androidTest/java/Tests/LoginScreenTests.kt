package Tests

import Gomoku.Login.LoginButtonBackTestTag
import Gomoku.Login.LoginButtonTestTag
import Gomoku.Login.LoginScreen
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
/*
class LoginScreenTests {
        @get:Rule
        val composeTestRule = createComposeRule()
    @Test
    fun click_on_login_button_calls_onLoginRequested() {
        // Arrange
        var loginRequested = false
        composeTestRule.setContent {
            LoginScreen(
                onBackRequested = { },
                loginFetch = { _, _ -> loginRequested = true }
            )
        }
        // Act
        composeTestRule.onNodeWithTag(LoginButtonTestTag).performClick()
        // Assert
        assertTrue(loginRequested)
    }

    @Test
    fun click_on_back_button(){
        // Arrange
        var backRequested = false
        composeTestRule.setContent {
            LoginScreen(
                onBackRequested = { backRequested = true },
                loginFetch = { _, _ ->  }
            )
        }
        // Act
        composeTestRule.onNodeWithTag(LoginButtonBackTestTag).performClick()
        // Assert
        assertTrue(backRequested)
    }

}

 */