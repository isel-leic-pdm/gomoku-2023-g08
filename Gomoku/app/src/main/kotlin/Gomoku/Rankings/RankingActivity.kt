package Gomoku.Rankings

import Gomoku.About.AboutScreen
import Gomoku.About.SocialInfo
import android.app.Application


import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels



class RankingActivity : ComponentActivity() {
    val app by lazy { application as Gomoku.Application }
    val viewModelRank by viewModels<RankingViewModel>()

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, RankingActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "AboutActivity.onCreate() called")

        setContent {
            RankingScreen(
                onBackRequested = { finish() },
               onFetchRankingRequest = { viewModelRank.getRanking(app.rankingService)}
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

private val socialLinks = emptyList<SocialInfo>()


private const val AUTHOR_EMAIL = "paulo.pereira@isel.pt"
private const val EMAIL_SUBJECT = "About the Jokes App"
