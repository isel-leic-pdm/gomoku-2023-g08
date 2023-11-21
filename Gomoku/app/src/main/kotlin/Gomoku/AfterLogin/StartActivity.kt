import Gomoku.CreateGame.CreateGameActivity
import Gomoku.AfterLogin.AfterLogged
import Gomoku.Rankings.RankingActivity
import Gomoku.ReplayGames.ReplayGameActivity
import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi

class StartActivity : ComponentActivity() {
    companion object {
        fun navigateTo(activity: ComponentActivity) {
            val intent = Intent(activity, StartActivity::class.java)
            activity.startActivity(intent)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AfterLogged(
                onCreateGameReq = {
                    CreateGameActivity.navigateTo(this)
                },
                onRankingReq = {
                    RankingActivity.navigateTo(this)
                },
                onReplayGameReq = {
                    ReplayGameActivity.navigateTo(this)
                }
                )
        }
    }
    override fun onStart() {
        super.onStart()
        Log.v(ContentValues.TAG, "MainActivity.onStart() called")
    }

    override fun onStop() {
        super.onStop()
        Log.v(ContentValues.TAG, "MainActivity.onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(ContentValues.TAG, "MainActivity.onDestroy() called")
    }
}
