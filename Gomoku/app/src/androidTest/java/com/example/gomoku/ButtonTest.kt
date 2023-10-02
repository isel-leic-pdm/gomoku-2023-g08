package com.example.gomoku

import Gomoku.ProjectAuthors.AuthorTestTag
import Gomoku.ProjectAuthors.DisplayAuthors
import Gomoku.ProjectAuthors.FetchButtonTestTag
import org.junit.Rule
import org.junit.Test

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick

class AutorsTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun screen_on_initial_state_does_not_show_Author() {
        composeRule.setContent {
            DisplayAuthors()
        }

        composeRule.onNodeWithTag(AuthorTestTag).assertDoesNotExist()
    }

    @Test
    fun screen_on_initial_state_shows_fetch_button() {
        composeRule.setContent {
            DisplayAuthors()
        }

        composeRule.onNodeWithTag(FetchButtonTestTag).assertExists()
    }
    @Test
    fun screen_shows_author_after_fetch_button_click() {
        // Arrange
        composeRule.setContent {
            DisplayAuthors()
        }

        // Act
        composeRule.onNodeWithTag(FetchButtonTestTag).performClick()

        // Assert
        composeRule.onNodeWithTag(AuthorTestTag).assertExists()
    }
}