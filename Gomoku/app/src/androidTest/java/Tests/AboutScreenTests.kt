package Tests


import Gomoku.About.AboutScreen
import Gomoku.ui.NavigateBackTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class AboutScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun click_on_back_navigation_calls_callback() {
        // Arrange
        var backRequested = false
        composeTestRule.setContent {
            AboutScreen(
                onBackRequested = { backRequested = true },
                socials = emptyList()
            )
        }
        // Act
        composeTestRule.onNodeWithTag(NavigateBackTestTag).performClick()
        // Assert
        assertTrue(backRequested)
    }
/*
    @Test
    fun click_on_author_info_calls_onSendEmailRequested() {
        // Arrange
        var sendEmailRequested = false
        composeTestRule.setContent {
            AboutScreen(
                onSendEmailRequested = { sendEmailRequested = true },
                socials = emptyList()
            )
        }
        // Act
        composeTestRule.onNodeWithTag(AuthorInfoTestTag).performClick()
        // Assert
        assertTrue(sendEmailRequested)
    }

    @Test
    fun click_on_socials_element_calls_onOpenUrlRequested_with_correct_url() {
        // Arrange
        var actualUrl: Uri? = null
        val expectedUrl = Uri.parse("https://www.test.pt")
        composeTestRule.setContent {
            AboutScreen(
                onOpenUrlRequested = { actualUrl = it },
                socials = listOf(SocialInfo(expectedUrl, R.drawable.ic_home))
            )
        }
        // Act
        composeTestRule.onNodeWithTag(SocialsElementTestTag).performClick()
        // Assert
        assertEquals(expectedUrl, actualUrl)
    }

 */
}
