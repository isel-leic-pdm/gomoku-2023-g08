package Gomoku.PlayGame

import Gomoku.CreateGame.WaitingRoomViewModel
import Gomoku.app.DependenciesContainer


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
    private val vm by viewModels<WaitingRoomViewModel> {
        WaitingRoomViewModel.factory((application as DependenciesContainer).userInfoRepository ,)
    }



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
                onPlayRequested = {
                    vm.play(app.playGameService, it.rowIndex, it.colIndex, app.usersService)
                    Log.v("123456", "play = $it")
                                  },
                viewModel = vm
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


