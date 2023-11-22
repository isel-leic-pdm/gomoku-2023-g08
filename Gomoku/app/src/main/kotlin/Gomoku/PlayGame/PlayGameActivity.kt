package Gomoku.PlayGame

import Gomoku.CreateGame.WaitingRoomViewModel


import Gomoku.app.GomokuApplication
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi


const val TAG1 = "GOMOKU_APP_TAG"

class PlayGameActivity : ComponentActivity() {

    val app by lazy { application as GomokuApplication }
    val viewModel by viewModels<WaitingRoomViewModel>()


    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, PlayGameActivity::class.java)
            origin.startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlayGameScreen(
                onBackRequested = { finish() },
                onPlayRequested = { viewModel.play(app.playGameService, it.rowIndex, it.colIndex) },


                )
        }
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG1, "MainActivity.onStart() called")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG1, "MainActivity.onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG1, "MainActivity.onDestroy() called")
    }
}

