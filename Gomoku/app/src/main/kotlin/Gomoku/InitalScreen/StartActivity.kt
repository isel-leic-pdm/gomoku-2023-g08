package Gomoku.InitalScreen

import Gomoku.About.AboutActivity

import Gomoku.Games.GameScreenViewModel
import Gomoku.Games.TAG

import Gomoku.CreateGame.CreateGameActivity
import Gomoku.Login.LoginActivity
import Gomoku.Rankings.RankingActivity
import Gomoku.User.UserActivity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi

class StartActivity : ComponentActivity() {
    private val viewModel by viewModels<GameScreenViewModel>()
   // private val app by lazy { application as Application }
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
            InitialScreen(
                onCreateUserReq = {
                 UserActivity.navigateTo(this)
                },
                onAboutreq = {
                    AboutActivity.navigateTo(this)
                },
                onCreateGameReq = {
                    CreateGameActivity.navigateTo(this)
                                  },

                onRankingReq = {
                    RankingActivity.navigateTo(this)
                },

                onLoginReq = {
                    LoginActivity.navigateTo(this)
                },
                
                /*
                onReplayGameReq = {
                    ReplayGameActivity.navigateTo(this)
                },
                onRankingReq = {
                    RankingActivity.navigateTo(this)
                },
                onAboutreq = {
                    AboutActivity.navigateTo(this)
                }

                 */

            )
        }
    }
    override fun onStart() {
        super.onStart()
        Log.v(TAG, "MainActivity.onStart() called")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "MainActivity.onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "MainActivity.onDestroy() called")
    }
}