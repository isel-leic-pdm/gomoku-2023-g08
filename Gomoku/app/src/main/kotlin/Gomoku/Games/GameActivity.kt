package Gomoku.Games

import Gomoku.About.AboutActivity
import Gomoku.DomainModel.BOARD_DIM
import Gomoku.GamesApplication
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


val cellSize = 20.dp
val lineSize = 2.dp
val boardSize = cellSize * BOARD_DIM + lineSize *(BOARD_DIM -1)

const val TAG = "GOMOKU_APP_TAG"

class GameActivity : ComponentActivity() {

    private val viewModel by viewModels<GameScreenViewModel>()
    private val app by lazy { application as GamesApplication }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           GameScreen(onInfoRequested = {
               AboutActivity.navigateTo(this)},
               onFetch = { viewModel.fetchGame(app.gamesService) }
           )
        }
    }
    override fun onStart() {
        super.onStart()
        Log.v(TAG, "MainActivity.onStart() called")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "MainActivity.onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "MainActivity.onDestroy() called")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun app() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        GameScreen()
    }

}


