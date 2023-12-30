package Gomoku.Login





import Gomoku.AfterLogin.LoggedActivity

import Gomoku.Landing.LandingActivity
import Gomoku.Main.Idle
import Gomoku.Main.Loaded
import Gomoku.State.IdleUser
import Gomoku.State.LoadedUser
import Gomoku.State.LoadingUser
import Gomoku.State.UserFailure
import Gomoku.State.UserSucess
import Gomoku.User.UsersViewModel
import Gomoku.app.DependenciesContainer
import Gomoku.app.GomokuApplication
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


class LoginActivity : ComponentActivity() {
    val app by lazy { application as GomokuApplication }
      companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, LoginActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            vm._user.collect { userState ->
                if (userState is UserSucess) {
                    Log.v("LOGIN", "LOGIN SUCCESS: $userState")
                    LoggedActivity.navigateTo(this@LoginActivity)
                } else {
                    if (userState is UserFailure) {
                        Log.v("LOGIN", "LOGIN FAILED: $userState")
                        // Lógica para lidar com a falha no login
                        Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT)
                            .show()
                        LoginActivity.navigateTo(this@LoginActivity)
                    }
                }

            }

        }
        setContent() {
            LoginScreen(
                onBackRequested = { finish() },
                onLoginFetchToken = {
                    vm.loginuser(app.usersService)
                },
                setUsername = vm::SetUser,
                setPassword = vm::setPass,
            )
            vm._user.let {
                if(it.value is UserSucess){
                    Log.v("LOGIN", "LOGIN SUCCESS: $it")
                    LoggedActivity.navigateTo(this@LoginActivity)
                }
                else{
                    if(it.value is UserFailure) {
                        Log.v("LOGIN", "LOGIN FAILED: $it")
                        // Lógica para lidar com a falha no login
                        Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT)
                            .show()
                        LoginActivity.navigateTo(this@LoginActivity)
                    }
                }
            }

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


/*
    fun doNavigation(login: Boolean) {
        Log.v("doNAV", "loginuser inside nav: " + login)
       if(login){
           LoggedActivity.navigateTo(this)
       }
        else {
            Log.v("doNAV", "loginuser: " + vm.loginSucess)
            LandingActivity.navigateTo(this)
              Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
       }
    }
*/
    private fun showMessage(message: String) {
        // Exemplo: exibir uma mensagem usando Toast
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private val vm by viewModels<UsersViewModel> {
        UsersViewModel.factory((application as DependenciesContainer).userInfoRepository)
    }
}