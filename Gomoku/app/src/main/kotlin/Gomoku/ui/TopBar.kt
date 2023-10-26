package Gomoku.ui


import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.gomoku.R

import isel.pdm.jokes.ui.theme.GameTheme

/**
 * Used to aggregate [TopBar] navigation handlers. If a handler is null, the corresponding
 * navigation element is not displayed.
 *
 * @property onBackRequested the callback invoked when the user clicks the back button.
 * @property onInfoRequested the callback invoked when the user clicks the info button.
 */
data class NavigationHandlers(
    val onBackRequested: (() -> Unit)? = null,
    val onInfoRequested: (() -> Unit)? = null,
)

// Test tags for the TopBar navigation elements
const val NavigateBackTestTag = "NavigateBack"
const val NavigateToInfoTestTag = "NavigateToInfo"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navigation: NavigationHandlers = NavigationHandlers()) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            if (navigation.onBackRequested != null) {

                   Button(onClick = navigation.onBackRequested, ) {
                       Text(text = "<-")
                   }
            }

        },
        actions = {
            if (navigation.onInfoRequested != null) {
                IconButton(
                    onClick = navigation.onInfoRequested,
                    modifier = Modifier.testTag(NavigateToInfoTestTag)
                ) {
                    /*
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = stringResource(id =
                        R.string.top_bar_navigate_to_about)
                    )

                     */
                }
            }
        }
    )}

@Preview
@Composable
private fun TopBarPreviewInfoAndHistory() {
    GameTheme {
        TopBar(
            navigation = NavigationHandlers(onInfoRequested = { })
        )
    }
}

@Preview
@Composable
private fun TopBarPreviewBackAndInfo() {
    GameTheme {
        TopBar(navigation = NavigationHandlers(onBackRequested = { }, onInfoRequested = { }))
    }
}

@Preview
@Composable
private fun TopBarPreviewBack() {
    GameTheme {
        TopBar(navigation = NavigationHandlers(onBackRequested = { }))
    }
}
