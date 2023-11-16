package Gomoku.ReplayGames

import Gomoku.About.SocialInfo
import Gomoku.app.GomokuApplication


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels



class ReplayGameActivity : ComponentActivity() {
    val app by lazy { application as GomokuApplication }
    val viewModelReplayGame by viewModels<ReplayGameViewModel>()

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, ReplayGameActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("REPLAYYYYYY", "ReplayGame.onCreate() called")

        setContent {
            ReplayGameScreen(
                onBackRequested = { finish() },
               fetchReplayGame = {
                   viewModelReplayGame.idGame = it
                   Log.v("IT123", "IT : "+ it.toString())
                   viewModelReplayGame.getGameSaved(app.saveGame)
                   ShowReplayGameActivity.navigateTo(ShowReplayGameActivity())
                                 },
            
            )
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

