package Gomoku.WaitingRoom

import Gomoku.CreateGame.WaitingRoomViewModel

import Gomoku.PlayGame.PlayGameActivity


import Gomoku.app.GomokuApplication


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels



class WaitingRoomActivity : ComponentActivity() {
    val app by lazy { application as GomokuApplication }
    val newGameActivity by viewModels<WaitingRoomViewModel>()

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, WaitingRoomActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "CREATE.onCreate() called")
        setContent {
            WaitingRoomScreen(
                onBackRequested = { finish() },
                onGameCreated = {  },
                onPlayCkick ={ PlayGameActivity.navigateTo(this)}
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
