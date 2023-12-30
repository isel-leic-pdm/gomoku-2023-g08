package Gomoku.AfterLogin
import Gomoku.CreateGame.CreateGameActivity
import Gomoku.DomainModel.BOARD_DIM
import Gomoku.Main.MainActivity
import Gomoku.Rankings.RankingActivity
import Gomoku.ReplayGames.ReplayGameActivity
import Gomoku.User.UsersViewModel
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
import androidx.compose.ui.unit.dp


val cellSize = 15.dp
val lineSize = 1.dp

const val TAG1 = "GOMOKU_APP_TAG"

class LoggedActivity : ComponentActivity() {

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, LoggedActivity::class.java)
            origin.startActivity(intent)
        }
    }
 // private val vm by viewModels<UsersViewModel>()
    private val app by lazy { application as GomokuApplication }

    private val vm by viewModels<UsersViewModel> {
        UsersViewModel.factory((application as DependenciesContainer).userInfoRepository)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AfterLogged(
                onCreateGameReq = { CreateGameActivity.navigateTo(this) },
                onReplayGameReq = { ReplayGameActivity.navigateTo(this) },
                onRankingReq = { RankingActivity.navigateTo(this) },
                   onLogOutReq = { MainActivity.navigateTo(this) },
                onFetchLogout = {vm.logout(app.usersService)},
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