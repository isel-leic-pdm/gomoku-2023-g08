package Gomoku.PlayGame

import Gomoku.InitalScreen.InitialScreen

import Gomoku.About.AboutActivity
import Gomoku.CreateGame.CreateGameActivity
import Gomoku.DomainModel.BOARD_DIM
import Gomoku.Games.GameScreenViewModel

import Gomoku.User.UserActivity
import Gomoku.app.GomokuApplication
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.ui.unit.dp




const val TAG1 = "GOMOKU_APP_TAG"

class PlayGameActivity : ComponentActivity() {


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
                onPlayRequested = {  },


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


