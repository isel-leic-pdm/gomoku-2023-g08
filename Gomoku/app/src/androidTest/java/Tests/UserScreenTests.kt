package Tests

import Gomoku.User.UserButtonTest
import Gomoku.User.UserScreen
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class UserScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun click_on_create_user(){
        // Arrange
        var createUserRequested = false
        composeTestRule.setContent {
            UserScreen(
               onBackRequested =  { },
            onfetchUsersRequested = { _, _ -> createUserRequested = true}


            )
        }
        // Act
        composeTestRule.onNodeWithTag(UserButtonTest).performClick()
        // Assert
        assertTrue(createUserRequested)
    }

}