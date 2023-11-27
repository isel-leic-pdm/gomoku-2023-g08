package Gomoku.Login





import Gomoku.AfterLogin.LoggedActivity
import Gomoku.AfterLogin.StartActivity

import Gomoku.DataStore.Domain.UserInfo
import Gomoku.Landing.LandingActivity
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
        Log.v(TAG, "UserActivity.onCreate() called")
        lifecycleScope.launch {
            vm.userInfoFlow.collect {
                if (it is Loaded) {
                    Log.v("LOGINSUCESS", "onCreate: " + vm.loginSucess)
                  doNavigation(vm.loginSucess)
                    vm.resetToIdle()
                }



            }
        }
            setContent {
                Log.v("USERS", "AboutActivity.onCreate() called")
                LoginScreen(
                    onBackRequested = { finish() },
                    onLoginFetchToken = {
                        vm.loginuser(app.usersService)
                       doNavigation(vm.loginSucess)
                                        },
                    loginFetch = {

                        Log.v("doNAV", "loginFEtch: " + vm.loginSucess)

                                    },
                    setUsername = vm::SetUser,
                    setPassword = vm::setPass,
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

    private fun showMessage(message: String) {
        // Exemplo: exibir uma mensagem usando Toast
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private val vm by viewModels<UsersViewModel> {
        UsersViewModel.factory((application as DependenciesContainer).userInfoRepository)
    }
}