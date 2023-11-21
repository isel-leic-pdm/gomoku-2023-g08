package Gomoku.Main

import Gomoku.About.AboutActivity


import Gomoku.Login.LoginActivity
import Gomoku.User.UserActivity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi


class MainActivity : ComponentActivity() {
    companion object {
        fun navigateTo(activity: ComponentActivity) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(
                onCreateUserReq = {
                    UserActivity.navigateTo(this)
                },
                onLoginReq = {
                    LoginActivity.navigateTo(this)
                },
                onAboutreq = {
                    AboutActivity.navigateTo(this)
                })
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