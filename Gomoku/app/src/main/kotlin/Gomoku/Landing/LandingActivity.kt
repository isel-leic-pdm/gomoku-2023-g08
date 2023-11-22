package Gomoku.Landing

import Gomoku.About.SocialInfo
import Gomoku.Main.MainActivity
import Gomoku.User.UserActivity
import Gomoku.app.GomokuApplication


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels



class LandingActivity : ComponentActivity() {
    val app by lazy { application as GomokuApplication }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, LandingActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "AboutActivity.onCreate() called")

        setContent {
            Log.v("LANDINGCREATED", "LandingActivity.onCreate() called")
            LandingScreen(
                onAheadRequested = { MainActivity.navigateTo(this) },
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
