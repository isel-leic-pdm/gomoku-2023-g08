package Gomoku.Login

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
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onBackRequested: () -> Unit = { },
    onfetchUsersRequested: () -> Unit = {  },
) {
    val usersViewModel = LoginViewModel()
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
            Button(onClick = onBackRequested) {
                Text("Back")
            }
        }

        Text("Username")
        TextField(
            value = nameInput.value,
            onValueChange = { nameInput.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Text("Password")
        TextField(
            value = passInput.value,
            onValueChange = { passInput.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Button(
            onClick = {
                // Handle submit button click
                usersViewModel.username = nameInput.value
                usersViewModel.password = passInput.value
                onfetchUsersRequested()


                // Do something with username and password
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Login")
        }
    }
}