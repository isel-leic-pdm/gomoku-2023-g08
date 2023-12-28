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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


const val TAG1 = "ONCREATE"

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


   fun onCreate2(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Log.v(TAG1, " onCreate called")
        vm.getGame()

        setContent {
            Log.v(TAG1, " setContent called")
            val gameState by vm.gameFlow.collectAsState()

            LaunchedEffect(gameState) {
                if (gameState is LoadedGameCreated) {
                    Log.v("GAMERES", "launchEffect: $gameState")
                }
            }

            PlayGameScreen(
                onBackRequested = { finish() },
                onPlayRequested = vm::play,
                game = gameState
            )
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
       vm.getGame()
        vm.startPolling()
               lifecycleScope.launch {
           vm.gameFlow.collectLatest {
               vm.getGame()
                setContent {
                     Log.v(TAG1, " setContent called")
                     val gameState by vm.gameFlow.collectAsState()

                     LaunchedEffect(gameState) {
                          if (gameState is LoadedGameCreated ) {
                            Log.v("GAMERES", "launchEffect: $gameState")
                          }
                         Log.v("GAMERES", "launchEffect outside if : $gameState")
                     }

                     PlayGameScreen(
                          onBackRequested = { finish() },
                          onPlayRequested = vm::play,
                          game = gameState
                     )
                }
           }
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


