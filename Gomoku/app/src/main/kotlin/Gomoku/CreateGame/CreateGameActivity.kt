package Gomoku.CreateGame

import Gomoku.NewGame.NewGameScreen
import Gomoku.User.UsersViewModel
import Gomoku.WaitingRoom.WaitingRoomActivity
import Gomoku.app.DependenciesContainer
import Gomoku.app.GomokuApplication


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


class CreateGameActivity : ComponentActivity() {
    val app by lazy { application as GomokuApplication }
    /*
    val vm by viewModels<WaitingRoomViewModel>()


     */
    private val vm by viewModels<WaitingRoomViewModel> {
        WaitingRoomViewModel.factory((application as DependenciesContainer).userInfoRepository)
    }





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
            NewGameScreen(
              onBackRequested = { finish() },
                onFetchCreateGameRequest = {
                       vm.createGame(app.createGameService)
                                           },
                onFetch = { WaitingRoomActivity.navigateTo(this) },
                setID = vm::setIDS,
                setOpeningRule =  vm::SetOpeningRules,
                setVariante =  vm::SetVariantes

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
