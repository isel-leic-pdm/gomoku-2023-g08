package Gomoku.ReplayGames

import Gomoku.State.LoadedListIds
import Gomoku.State.LoadedSaveReplayGame
import Gomoku.State.loadListIds
import Gomoku.State.loadSaveReplayGame
import Gomoku.ui.BoardView
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplayGameScreen(
    onBackRequested: () -> Unit = { },
    onPreviousRequested: () -> Unit = {},
    onNextRequested: () -> Unit = {},
    fetchReplayGame: () -> Unit = { },
    fetchGamesPlayed: () -> Unit,
    listIds: loadListIds,
    game: loadSaveReplayGame,
    index: Int,
    setIdGame: (Int) -> Unit = { },
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Button to fetch all game IDs
        Button(onClick = { fetchGamesPlayed() }) {
            Text("Get all Game IDs")
        }

        if (listIds is LoadedListIds) {
            val allIds = listIds.result.getOrNull()
            if (!allIds.isNullOrEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("All Game IDs:")
                    for (id in allIds) {
                        Text(id.toString())
                    }
                }
            } else {
                Text("No Game IDs available.")
            }
        }
        // Input for entering game ID
        val gameInput = remember { mutableStateOf("") }
        TextField(
            value = gameInput.value,
            onValueChange = { gameInput.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Enter Game ID") }
        )

        // Button to fetch and replay the game
        Button(
            onClick = {
                setIdGame(gameInput.value.toInt())
                fetchReplayGame()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Replay Game")
        }

        // Check if the state is LoadedSaveReplayGame
        if (game is LoadedSaveReplayGame) {
            val allgames = game.result.getOrNull()
            if(allgames != null) {
                val loadedGame = allgames.get(index)

                // Log the value of loadedGame
                Log.v("ReplayGameScreen", "Loaded Game: $loadedGame")

                // BoardView to display the game board
                BoardView(board = loadedGame.board, onclick = { _, _ -> })

                // Game details
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Turn: ${loadedGame.turn}")
                    Text("Variante: ${loadedGame.variant}")
                    Text("Regra de abertura: ${loadedGame.openingRule}")
                    if (index == allgames.size - 1) {
                        Text("Winner: ${loadedGame.turn}")
                    }
                }

                // Previous and Next buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = onPreviousRequested) {
                        Text("Previous")
                    }
                    Button(onClick = onNextRequested) {
                        Text("Next")
                    }
                }
            }
        }

        // Back button
        Button(
            onClick = onBackRequested,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Back")
        }
    }
}
