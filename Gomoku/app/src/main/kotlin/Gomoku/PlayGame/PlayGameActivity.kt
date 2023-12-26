package Gomoku.PlayGame

import Gomoku.CreateGame.WaitingRoomViewModel
import Gomoku.State.LoadedGameCreated
import Gomoku.app.DependenciesContainer


import Gomoku.app.GomokuApplication
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


const val TAG1 = "GOMOKU_APP_TAG"

class PlayGameActivity : ComponentActivity() {

    private val vm by viewModels<WaitingRoomViewModel> {
        WaitingRoomViewModel.factory((application as DependenciesContainer).userInfoRepository,(application as DependenciesContainer).gameService, (application as DependenciesContainer).playGameService )
    }
    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, PlayGameActivity::class.java)
            origin.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.getGame()
        setContent() {
            PlayGameScreen(
                onBackRequested = { finish() },
                onPlayRequested = vm::play,
                game = vm.gameMT
            )
        }
    }
    override fun onStart() {
        super.onStart()
        Log.v(TAG1, " playGame activity called")
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


