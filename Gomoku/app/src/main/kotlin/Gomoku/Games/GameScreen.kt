package Gomoku.Games


import Gomoku.ErrorAlert
import Gomoku.State.IdleGame
import Gomoku.State.LoadStateGame
import Gomoku.State.LoadedGame
import Gomoku.ui.BoardView
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameScreen(game:LoadStateGame = IdleGame, onInfoRequested: () -> Unit = { }, onFetch: () -> Unit = { }) {
    val vm = remember { GameScreenViewModel() }
    Log.v("GameScreen", "GameScreen called")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.Cyan)
            .fillMaxSize()
    ) {
        Button(
            modifier = Modifier.testTag("InfoButton"),
            onClick = onInfoRequested
        ) {
            Text("InfoButton")
        }
        Button(
            modifier = Modifier.testTag("MatchMakingButton"),
            onClick = onFetch
        ) {

            Text("MatchMakingButton")
        }
        if(game is LoadedGame) {
            game.result.getOrNull()?.let {it ->
                BoardView(vm, it.board, onclick = {  })
            }
       }
      //  PlayerView(vm)
      //  Turn()
        if (game is LoadedGame && game.result.isFailure) {
            ErrorAlert(
                title = "Error",
                message = 1,
                buttonText = 2,
                onDismiss = onFetch
            )
        }
    }
}
/*
        Row( modifier = Modifier.testTag("BoardRow"), verticalAlignment = Alignment.CenterVertically) {
               vm.newGame(servicegame = serviceGame)
                BoardView(vm, vm.game?.board, onclick = { vm.play(it) })
            }

        }

    Column(horizontalAlignment = Alignment.End, modifier = Modifier) {
        Text("GameScreen")
        GameScreenViewModel()
    }
}

 */



