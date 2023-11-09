package Gomoku.NewGame

import Gomoku.CreateGame.WaitingRoomViewModel
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.toOpeningRule
import Gomoku.DomainModel.toVariante
import Gomoku.DomainModel.variantes
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
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGameScreen(
    onBackRequested: () -> Unit = { },
    onFetchCreateGameRequest: (Int, openingrule, variantes) -> Unit = { _, _, _ -> },
    onFetch : () -> Unit = { }
) {

val gameViewModel = WaitingRoomViewModel()
    val varianteInput = remember { mutableStateOf("") }
    val openingRuleInput = remember { mutableStateOf("") }
    val idInput = remember { mutableStateOf("") }

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
        Text("Id")
        TextField(
            value = idInput.value,
            onValueChange = { idInput.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Text("Variante")
        TextField(
            value = varianteInput.value,
            onValueChange = { varianteInput.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Text("Opening Rule")
        TextField(
            value = openingRuleInput.value,
            onValueChange = { openingRuleInput.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Button(
            onClick = {
                // Handle submit button click
                //gameViewModel.id = idInput.value
                gameViewModel.variante = varianteInput.value
                gameViewModel.openingRule = openingRuleInput.value

                onFetchCreateGameRequest(idInput.value.toInt(), gameViewModel.openingRule.toOpeningRule(), gameViewModel.variante.toVariante())

                // Do something with username and password
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Create Game")
        }
    }
}
@Composable
fun WaitingLobbyScreen() {
    Log.v("WaitingLobbyScreen", "WaitingLobbyScreen called")
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Waiting Lobby")
    }
}
