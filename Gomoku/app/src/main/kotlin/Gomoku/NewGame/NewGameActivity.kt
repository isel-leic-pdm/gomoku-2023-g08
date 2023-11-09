package Gomoku.NewGame


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

/*
class NewGameActivity : ComponentActivity() {
    val app by lazy { application as Application }
    val viewModel by viewModels<NewGameModel>()


    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, NewGameActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "NewGameActivity.onCreate() called")

        setContent {
            Log.v("NewGame", "NewGameActivity.onCreate() called")
            NewGameScreen(onBackRequested = { finish() },
                onfetchUsersRequested = { },
                )
        }
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG, "NewGameActivity.onStart() called")
    }
    override fun onStop() {
        super.onStop()
        Log.v(TAG, "NewGameActivity.onStop() called")
    }



}

 */



