package Gomoku.About



import Gomoku.ui.NavigationHandlers
import Gomoku.ui.TopBar
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gomoku.R
import isel.pdm.jokes.ui.theme.GameTheme

import kotlin.math.min

/**
 * Used to represent information about a social network in the about screen
 * @param link the link to the social network
 * @param imageId the id of the image to be displayed
 */
data class SocialInfo(val link: Uri, @DrawableRes val imageId: Int)

/**
 * Tags used to identify the components of the AboutScreen in automated tests
 */
const val AboutScreenTestTag = "AboutScreenTestTag"

/**
 * Root composable for the about screen, the one that displays information about the app.
 * @param onBackRequested the callback to be invoked when the user requests to go back to the
 * previous screen
 * @param onSendEmailRequested the callback to be invoked when the user requests to send an email
 * @param onOpenUrlRequested the callback to be invoked when the user requests to open an url
 * @param socials the social networks' links to be displayed
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBackRequested: () -> Unit = { },
    onSendEmailRequested: () -> Unit = { },
    onOpenUrlRequested: (Uri) -> Unit = { },
    socials: List<SocialInfo>
) {
    GameTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .testTag(AboutScreenTestTag),
            topBar = { TopBar(NavigationHandlers(onBackRequested = onBackRequested)) },
        ) { innerPadding ->
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            ) {
                Author(onSendEmailRequested = onSendEmailRequested)
                Socials(
                    socials = socials,
                    onOpenUrlRequested = onOpenUrlRequested
                )
            }
        }
    }
}

/**
 * Composable used to display information about the author of the application
 */
@Composable
private fun Author(onSendEmailRequested: () -> Unit = { }) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier

        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_ricardo),
                contentDescription = null,
                modifier = Modifier.sizeIn(100.dp, 100.dp, 150.dp, 150.dp)
            )
            Text(text = "Ricardo Oliveira", style = MaterialTheme.typography.bodyMedium)
            Row {
                Image(painter = painterResource(id = R.drawable.ic_email),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clickable(onClick = { onSendEmailRequested() }
                        ))
            }

        }


        //  Icon(imageVector = Icons.Default.Email, contentDescription = null)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier

        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_author),
                contentDescription = null,
                modifier = Modifier.sizeIn(100.dp, 100.dp, 200.dp, 200.dp)
            )
            Text(text = "Goncalo Pinto", style = MaterialTheme.typography.bodyMedium)
            Row {
                Image(painter = painterResource(id = R.drawable.ic_email),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clickable(onClick = { onSendEmailRequested() }
                        ))
            }

        }
    }
}

@Composable
private fun Socials(
    onOpenUrlRequested: (Uri) -> Unit = { },
    socials: List<SocialInfo>
) {
    val countPerRow = 4
    repeat(socials.size / countPerRow + 1) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            val start = it * countPerRow
            val end = min(a = (it + 1) * countPerRow, b = socials.size)
            socials.subList(fromIndex = start, toIndex = end).forEach {
                Social(id = it.imageId, onClick = { onOpenUrlRequested(it.link) })
            }
        }
    }
}

@Composable
private fun Social(@DrawableRes id: Int, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = id),
        contentDescription = null,
        modifier = Modifier
            .sizeIn(maxWidth = 64.dp)
            .clickable { onClick() }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InfoScreenPreview() {

}
