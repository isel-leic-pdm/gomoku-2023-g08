package Gomoku.Main

import Gomoku.About.AboutActivity
import Gomoku.MainScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.tds.gomoku.model.BOARD_DIM

val cellSize = 20.dp
val lineSize = 2.dp
val boardSize = cellSize * BOARD_DIM + lineSize *(BOARD_DIM -1)

const val TAG = "GOMOKU_APP_TAG"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           MainScreen(onInfoRequested = {
               AboutActivity.navigateTo(this)
           })
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun app() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        MainScreen()
    }

}


