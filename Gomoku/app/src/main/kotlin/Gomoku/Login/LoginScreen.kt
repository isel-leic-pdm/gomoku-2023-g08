package Gomoku.Login



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

val LoginButtonTestTag = "LoginButton"
val LoginButtonBackTestTag = "LoginButtonBack"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onBackRequested: () -> Unit = { },
    onLoginFetchToken : () -> Unit = {  },
    loginFetch: () -> Unit = {  },
    setUsername: (String) -> Unit = {  } ,
    setPassword: (String) -> Unit = {  }
) {
val nameInput = remember { mutableStateOf("") }
    val passInput = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(onClick = onBackRequested, modifier = Modifier.testTag(LoginButtonBackTestTag)) {
                Text("Back")
            }
        }

        Text("Username")
        TextField(
            value = nameInput.value,
            onValueChange = { nameInput.value = it
                            setUsername(nameInput.value)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Text("Password")
        TextField(
            value =  passInput.value,
            onValueChange = {
                passInput.value = it
                setPassword(passInput.value)
                           },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Button(
            onClick = {
                onLoginFetchToken()
                      },
            modifier = Modifier.padding(16.dp).testTag(LoginButtonTestTag)
        ) {
            Text("Login")
        }
    }
}
