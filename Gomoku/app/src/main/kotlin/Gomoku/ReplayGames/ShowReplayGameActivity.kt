package Gomoku.ReplayGames


import Gomoku.State.IdleSaveReplayGame
import Gomoku.State.LoadedSaveReplayGame
import Gomoku.State.ReplayGameModel
import Gomoku.State.loadSaveReplayGame
import Gomoku.app.GomokuApplication


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


class ShowReplayGameActivity() : ComponentActivity() {

    val app by lazy { application as GomokuApplication }
    val viewModelReplayGame by viewModels<ReplayGameViewModel>()

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, ShowReplayGameActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("SLB", "AboutActivity.onCreate() called")
        setContent {
            ShowReplayGameScreen(
                onBackRequested = { finish() },
                fetchReplayGame = {
                    viewModelReplayGame.getGameSaved(app.saveGame)
                },

                games = viewModelReplayGame.gamedReplay

                )
            if(viewModelReplayGame.gamedReplay is LoadedSaveReplayGame)
                ShowView(game = (viewModelReplayGame.gamedReplay as LoadedSaveReplayGame).result.getOrThrow())

            Log.v("EIU","game : "+ viewModelReplayGame.gamedReplay.toString())
        }
    }



    override fun onStart() {
        super.onStart()
        Log.v(TAG, "AboutActivity.onStart() called")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "AboutActivity.onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "AboutActivity.onDestroy() called")
    }
}
@Composable
fun ShowReplayGameScreen(
    onBackRequested: () -> Unit = { },
    fetchReplayGame: () -> Unit = { },
    games: loadSaveReplayGame) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Log.v("EIU-SHOW", "ShowReplayGameScreen called")
        Button(onClick = onBackRequested) {
            Text("Back")
        }
    }
    Log.v("EIU-SHOW", "fetch:" + games.toString())
       if (games is LoadedSaveReplayGame) {
            Log.v("EIU-SHOW", "fetch:" + games.toString())

            ShowView(game = games.result.getOrThrow())
        }


    }

@Composable
fun ShowView(game: List<ReplayGameModel>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        game.forEach { game ->
            Text(text = game.game_id.toString())
            Text(text = game.player.toString())
           Text(text = game.turn.toString())
           Text(text = game.line.toString())
           Text(text = game.col.toString())
            Text(text = game.board.toString())
        }
    }
}

