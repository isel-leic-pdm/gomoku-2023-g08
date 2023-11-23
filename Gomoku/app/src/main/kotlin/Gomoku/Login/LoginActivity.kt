package Gomoku.Login





import Gomoku.AfterLogin.LoggedActivity

import Gomoku.DataStore.Domain.UserInfo
import Gomoku.Main.Loaded
import Gomoku.Main.MainScreenViewModel
import Gomoku.Main.getOrNull
import Gomoku.User.UsersViewModel
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


class LoginActivity : ComponentActivity() {
    val app by lazy { application as GomokuApplication }
    val viewModel by viewModels<UsersViewModel>()

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, LoginActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "UserActivity.onCreate() called")
        lifecycleScope.launch {
            vm.userInfoFlow.collect {
                if (it is Loaded) {
                    doNavigation(it.getOrNull())
                    vm.resetToIdle()
                }
            }
        }
            setContent {
                Log.v("USERS", "AboutActivity.onCreate() called")
                LoginScreen(
                    onBackRequested = { finish() },
                    onLoginFetchToken = { viewModel.loginuser(app.usersService) },
                    loginFetch = {
                        LoggedActivity.navigateTo(this)
                        vm.rightUserInfo()

                    },
                    setUsername = viewModel::SetUser,
                    setPassword = viewModel::setPass,
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

    private val vm by viewModels<UsersViewModel> {
        UsersViewModel.factory((application as DependenciesContainer).userInfoRepository)
    }

    private fun doNavigation(userInfo: UserInfo?) {
        if (userInfo == null)
            LoginActivity.navigateTo(this)
        else
            LoggedActivity.navigateTo(this)
    }
}