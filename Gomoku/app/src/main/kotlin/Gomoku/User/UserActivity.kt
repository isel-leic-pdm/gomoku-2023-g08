package Gomoku.User


import Gomoku.app.GomokuApplication
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels


class UserActivity : ComponentActivity() {
    val app by lazy { application as GomokuApplication}
    val viewModel by viewModels<UsersViewModel>()


    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, UserActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "UserActivity.onCreate() called")

        setContent {
            Log.v("USERS", "AboutActivity.onCreate() called")
            UserScreen(onBackRequested = { finish() },
                onfetchUsersRequested = { username, password ->
                    viewModel.createUser(username,password,app.usersService)


                }
            )
        }
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG, "UserActivity.onStart() called")
    }
    override fun onStop() {
        super.onStop()
        Log.v(TAG, "UserActivity.onStop() called")
    }



}



