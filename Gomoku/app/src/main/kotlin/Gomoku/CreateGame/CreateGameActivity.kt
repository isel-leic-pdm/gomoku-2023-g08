package Gomoku.CreateGame

import Gomoku.NewGame.CreateGameScreen
import Gomoku.app.GomokuApplication


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels



class CreateGameActivity : ComponentActivity() {
    val app by lazy { application as GomokuApplication }
    val createGameActivity by viewModels<WaitingRoomViewModel>()

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, CreateGameActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "CREATE.onCreate() called")
        setContent {
            CreateGameScreen(
              onBackRequested = { finish() },
                onFetchCreateGameRequest = { id, variante, openingRule -> createGameActivity.createGame(app.createGameService, id, variante, openingRule) },
            )
            Log.v("aaaa", "CreateGameActivity.onCreate() called ${createGameActivity.id}, ${createGameActivity.waitGame}, ${createGameActivity.openingRule}, ${createGameActivity.variante}")

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
