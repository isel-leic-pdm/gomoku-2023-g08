package Gomoku.NewGame

import Gomoku.CreateGame.WaitingRoomViewModel
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.toOpeningRule
import Gomoku.DomainModel.toVariante
import Gomoku.DomainModel.variantes
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewGameScreen(
    onBackRequested: () -> Unit = { },
    onFetchCreateGameRequest: () -> Unit = {   },
    setOpeningRule: (String) -> Unit = { },
    setVariante: (String) -> Unit = { },
) {
    val selectedVariante = remember { mutableStateOf("") }
    val selectedOpeningRule = remember { mutableStateOf("") }

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
        Text("Variante")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    selectedVariante.value = "OMOK"
                    setVariante(selectedVariante.value)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .testTag("OMOK")
            ) {
                Text("OMOK")
            }

            Button(
                onClick = {
                    selectedVariante.value = "NORMAL"
                    setVariante(selectedVariante.value)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .testTag("NORMAL")
            ) {
                Text("NORMAL")
            }
        }

        Text("Opening Rule")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    selectedOpeningRule.value = "PRO"
                    setOpeningRule(selectedOpeningRule.value)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .testTag("PRO")
            ) {
                Text("PRO")
            }

            Button(
                onClick = {
                    selectedOpeningRule.value = "NORMAL"
                    setOpeningRule(selectedOpeningRule.value)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .testTag("NORMALL")
            ) {
                Text("NORMAL")
            }
        }

        Button(
            onClick = {
                onFetchCreateGameRequest()

            },
            modifier = Modifier.padding(16.dp).testTag("CreateGameButton")
        ) {
            Text("Create Game")
        }
    }

}