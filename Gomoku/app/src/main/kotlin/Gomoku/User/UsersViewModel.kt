package Gomoku.User



import Gomoku.Services.UsersService
import Gomoku.State.IdleUser
import Gomoku.State.LoadStateUser
import Gomoku.State.LoadedUser
import Gomoku.State.LoadingUser

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class UsersViewModel() : ViewModel() {


    var user by mutableStateOf<LoadStateUser>(IdleUser)
        private set
    var password : String =""
    var username : String =""

    fun createUser(username : String, password: String, usersService: UsersService): Unit {
        viewModelScope.launch {
            user = LoadingUser
            user = LoadedUser(
                runCatching {
                        usersService.createuser(username, password, usersService)

                }
            ) }
}
    fun loginuser(serviceuser: UsersService): Unit {
        viewModelScope.launch {
            user = LoadingUser
            user = LoadedUser(
                runCatching {
                    serviceuser.loginuser()
                }
            )
        }
    }

    }














