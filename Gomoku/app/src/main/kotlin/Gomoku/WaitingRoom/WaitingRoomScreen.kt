package Gomoku.WaitingRoom

import Gomoku.PlayGame.PlayGameActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun WaitingRoomScreen(
    onBackRequested: () -> Unit = { },
    onGameCreated : () -> Unit = { },
    onPlayCkick : () -> Unit = { }
) {
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
        Text("Waiting Room")

        Button(onClick ={
           onPlayCkick()
            onGameCreated()
        }) {
            Text("Play")
        }
    }
}