package Tests

import Gomoku.About.AboutScreen
import Gomoku.About.UserScreen
import Gomoku.AfterLogin.AboutButtonTestTag
import Gomoku.AfterLogin.CreateUserButtonTestTag
import Gomoku.AfterLogin.NewGameButtonTestTag
import Gomoku.AfterLogin.RankingButtonTestTag
import Gomoku.Login.LoginButtonTestTag
import Gomoku.Login.LoginScreen
import Gomoku.NewGame.NewGameScreen
import Gomoku.Rankings.RankingScreen
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class InitialScreenTest {
           @get:Rule
            val composeTestRule = createComposeRule()
        @Test
        fun CreateButtonExistsInInitialScreen(){
            composeTestRule.setContent {
                UserScreen(
                    onBacl
                )
            }
            composeTestRule.onNodeWithTag(CreateUserButtonTestTag).assertExists()
        }
        @Test
        fun LoginUserButtonExistsInInitialScreen(){
            composeTestRule.setContent {
                LoginScreen(
                    onBackRequested = { },
                    loginFetch = { _, _ ->  }
                )
            }
            composeTestRule.onNodeWithTag(LoginButtonTestTag).assertExists()
        }
        @Test
        fun NewGameButtonExistsInInitialScreen(){
            composeTestRule.setContent {
                NewGameScreen(
                    onBackRequested = { },
                )
            }
            composeTestRule.onNodeWithTag(NewGameButtonTestTag).assertExists()
        }
    /*
        @Test
        fun ReplayGameButtonExistsInInitialScreen(){
            composeTestRule.setContent {
                ReplayGameS(
                    onBackRequested = { },
                    loginFetch = { _, _ ->  }
                )
            }
            composeTestRule.onNodeWithTag(ReplayGameButtonTestTag).assertExists()
        } */
        @Test
        fun RankingButtonExistsInInitialScreen(){
            composeTestRule.setContent {
                RankingScreen(
                    onBackRequested = { },
                )
            }
            composeTestRule.onNodeWithTag(RankingButtonTestTag).assertExists()
        }
    @Test
    fun AboutButtonExistsInInitialScreen(){
        composeTestRule.setContent {
            AboutScreen(
                onBackRequested = { },
                onSendEmailRequested = { },
                onOpenUrlRequested = { },
                socials = listOf()
            )
        }
        composeTestRule.onNodeWithTag(AboutButtonTestTag).assertExists()
    }
}