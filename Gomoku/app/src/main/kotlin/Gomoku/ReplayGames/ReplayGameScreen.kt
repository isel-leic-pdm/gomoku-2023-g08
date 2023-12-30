package Gomoku.ReplayGames

import Gomoku.State.LoadedSaveReplayGame
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
    game: loadSaveReplayGame,
    index: Int,
    allMoves: Int,
    setIdGame: (Int) -> Unit = { },
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val gameInput = remember { mutableStateOf("") }


        Button(onClick = onBackRequested) {
            Text("Back")
        }

        TextField(
            value = gameInput.value,
            onValueChange = { gameInput.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Button(onClick = {
            setIdGame(gameInput.value.toInt())
            fetchReplayGame()
        }) {
            Text("ReplayGame")
        }



        // Check if the state is LoadedSaveReplayGame
        if (game is LoadedSaveReplayGame) {
            val allgames = game.result.getOrNull()
            val loadedGame = allgames?.get(index)
            //allMoves = allgames?.size ?: 0

            // Log o valor de loadedGame
            Log.v("ReplayGameScreen", "Loaded Game: $loadedGame")

            // Exibe BoardView com os dados do jogo carregado
            BoardView(board = loadedGame!!.board, onclick = { _, _ -> })
            Row {
                Text("Turn: ${loadedGame.turn}")
                Text("Variante: ${loadedGame.variant}")
                Text("Regra de abertura: ${loadedGame.openingRule}")
               if(index == allgames.size-1){
                 Text()
               }
            }

            Row {
                Button(onClick = onPreviousRequested) {
                    Text("Anterior")
                }
                Button(onClick = onNextRequested) {
                    Text("Próximo")
                }
            }
        }


    }
}
