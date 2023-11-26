package Gomoku.ReplayGames

import Gomoku.State.IdleSaveReplayGame
import Gomoku.State.IdleUserRank
import Gomoku.State.LoadStateUserRank
import Gomoku.State.LoadedSaveReplayGame
import Gomoku.State.LoadedUserRank
import Gomoku.State.ReplayGameModel
import Gomoku.State.loadSaveReplayGame
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
fun ReplayGameScreen(
    onBackRequested: () -> Unit = { },
    fetchReplayGame: () -> Unit = { },
    onfetch : () -> Unit = { },
    setIdGame: (Int) -> Unit = { },
    game: loadSaveReplayGame = IdleSaveReplayGame) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally

        ) {

        val gameInput = remember { mutableStateOf("") }

        Log.v("SAVEGAME","game1 : "+ game.toString())
        Button(onClick = onBackRequested) {
            Text("Back")
        }

        TextField(
            value = gameInput.value,
            onValueChange = { gameInput.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Button(onClick = {
            Log.v("SAVEGAME","gameInput : "+ gameInput.value)
            setIdGame(gameInput.value.toInt())
            fetchReplayGame()
            onfetch() }) {
            Text("ReplayGame")
        }

    }


    }


@Composable
fun ReplayGameView(game: List<ReplayGameModel>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            game.forEach {
                Text("Game id: " + it.game_id.toString())
                Text("Player: " + it.player.toString())
                Text("Turn: " + it.turn.toString())
                Text("Line: " + it.line.toString())
                Text("Col: " + it.col.toString())
                Text("Board: " + it.board.toString())
            }
        }
    }

}

