package Tests

import Gomoku.AfterLogin.AfterLogged
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class AfterLoginTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun should_call_onCreateGameReq_when_New_Game_button_is_clicked() {
        // Arrange
        var onCreateGameReqCalled = false
        val onCreateGameReq: () -> Unit = { onCreateGameReqCalled = true }

        composeTestRule.setContent {
            AfterLogged(onCreateGameReq = onCreateGameReq)
        }

        // Act
        composeTestRule.onNodeWithText("New Game").performClick()

        // Assert
        assertTrue(onCreateGameReqCalled)
    }

    @Test
    fun should_call_onReplayGameReq_when_Replay_Game_button_is_clicked() {
        // Arrange
        var onReplayGameReqCalled = false
        val onReplayGameReq: () -> Unit = { onReplayGameReqCalled = true }

        composeTestRule.setContent {
            AfterLogged(onReplayGameReq = onReplayGameReq)
        }

        // Act
        composeTestRule.onNodeWithText("Replay Game").performClick()

        // Assert
        assertTrue(onReplayGameReqCalled)
    }

}