package Gomoku.User

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

val UserButtonTest = "UserButton"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    onBackRequested: () -> Unit = { },
    onfetchUsersRequested: () -> Unit = {  },
    onfetch : () -> Unit = { },
    SetUsername: (String) -> Unit = { },
    SetPassword: (String) -> Unit = { },
) {

    val usernameInput = remember { mutableStateOf("") }
    val passwordInput = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(onClick = onBackRequested) {
                Text("Back")
            }
        }

        Text("Username")
        TextField(
            value = usernameInput.value,
            onValueChange = { usernameInput.value = it
                SetUsername(usernameInput.value)
                                          },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
       Text("Password")
        TextField(
            value = passwordInput.value,
            onValueChange = { passwordInput.value = it
                            SetPassword(passwordInput.value)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )


        Button(
            onClick = {
                onfetchUsersRequested()
                onfetch()

            },
            modifier = Modifier.padding(16.dp).testTag(UserButtonTest)
        ) {
            Text("Submit")
        }
    }
}