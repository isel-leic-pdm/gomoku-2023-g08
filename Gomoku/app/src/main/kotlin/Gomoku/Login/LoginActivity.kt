package Gomoku.Login

import Gomoku.Application
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels


class LoginActivity : ComponentActivity() {
    val app by lazy { application as Application }
    val viewModel by viewModels<LoginViewModel>()


    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, LoginActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "UserActivity.onCreate() called")

        setContent {
            Log.v("USERS", "AboutActivity.onCreate() called")
            LoginScreen(onBackRequested = { finish() },
                onfetchUsersRequested = { viewModel.loginuser(app.usersService)},
                )
        }
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG, "LoginActivity.onStart() called")
    }
    override fun onStop() {
        super.onStop()
        Log.v(TAG, "LoginActivity.onStop() called")
    }



}



