package Gomoku

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import isel.pdm.jokes.ui.theme.GameTheme


const val ErrorAlertTestTag = "ErrorAlertTestTag"

@Composable
fun ErrorAlert(
   title: String,
    message: Int,
    buttonText: Int,
    onDismiss: () -> Unit = { }
) {
    ErrorAlertImpl(
        title = stringResource(id = title.length),
        message = stringResource(id = message),
        buttonText = stringResource(id = buttonText),
        onDismiss = onDismiss
    )
}

@Composable
private fun ErrorAlertImpl(
    title: String,
    message: String,
    buttonText: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            OutlinedButton(
                border = BorderStroke(0.dp, Color.Unspecified),
                onClick = onDismiss
            ) {
                Text(text = buttonText)
            }
        },
        title = { Text(text = title) },
        text = { Text(text = message) },
        icon = {
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "Warning"
            )
        },
        modifier = Modifier.testTag(ErrorAlertTestTag)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ErrorAlertImplPreview() {
    GameTheme {
        ErrorAlertImpl(
            title = "Error accessing server",
            message = "Could not ...",
            buttonText = "OK",
            onDismiss = { }
        )
    }
}
