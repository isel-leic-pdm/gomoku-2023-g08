package Gomoku.User



import Gomoku.AfterLogin.LoggedActivity
import Gomoku.DataStore.Domain.UserInfo
import Gomoku.DataStore.Domain.UserInfoRepository
import Gomoku.DomainModel.Users
import Gomoku.Main.Idle
import Gomoku.Main.LoadState
import Gomoku.Main.idle
import Gomoku.Main.loaded
import Gomoku.Main.loading
import Gomoku.Services.UsersService
import Gomoku.State.IdleUser
import Gomoku.State.LoadStateUser
import Gomoku.State.LoadedUser
import Gomoku.State.LoadingUser
import Gomoku.State.UserFailure
import Gomoku.State.UserSucess
import android.util.Log

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersViewModel(private val repository: UserInfoRepository) : ViewModel() {
   var _user : MutableStateFlow<LoadStateUser> = MutableStateFlow(IdleUser)
    var user: LoadStateUser
        get() = _user.value
        set(value) {
            _user.value = value
        }

    var id by mutableStateOf(0)
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var token by mutableStateOf("")





    private var _userInfoFlow: MutableStateFlow<LoadState<UserInfo?>> = MutableStateFlow(idle())
    val userInfoFlow: Flow<LoadState<UserInfo?>>
        get() = _userInfoFlow.asStateFlow()
    fun rightUserInfo(id: Int?, username: String?, password: String?, token: String?) {
        try {
            if(_userInfoFlow.value is Idle){
                    _userInfoFlow.value = loading()
                    viewModelScope.launch {
                        repository.updateUserInfo(UserInfo(id, username, password, token))
                        val result = runCatching { repository.getUserInfo() }
                        _userInfoFlow.value = loaded(result)

                    }
                    Log.v("12345", "user flow if : " +"${_userInfoFlow.value}")
                }


                Log.v("12345", "user flow : " +"${_userInfoFlow.value}")
            } catch (e: Exception) {
                // Tratar a exceção, se necessário
                Log.e("12345", "Error in rightUserInfo", e)
            }
        }




    fun resetToIdle() {
        _userInfoFlow.value = idle()
    }

    companion object {
        fun factory(repository: UserInfoRepository) = viewModelFactory {
            initializer { UsersViewModel(repository) }
        }
    }




    fun SetUser(username: String) {
        this.username = username
    }
    fun setPass(password: String) {
        this.password = password
    }


    fun createUser(usersService: UsersService): Unit {
        viewModelScope.launch {
            user = LoadingUser
            user = LoadedUser(
                runCatching {
                        usersService.createuser(username, password, usersService)

                }
            ) }
}

    fun loginuser(usersService: UsersService) {
        Log.v("USERS", "$username $password aqui")
        viewModelScope.launch {
            user = LoadingUser
            try {
                val res = usersService.loginuser(username, password)
                Log.v("doNav", "after try log ${res.id}")
                if (res.id == null) {
                    user = UserFailure
                    return@launch


                } else {
                    Log.v("LOGIN", "user = userSucess")
                    user= UserSucess
                    rightUserInfo(res.id, res.name, res.password, res.token)
                    return@launch
                }
            } catch (e: Exception) {
                // Tratar exceções, se necessário
                user = UserFailure
                Log.e("doNav", "Exception: ${e.message}")
            }
        }
    }

fun logout(usersService: UsersService): Unit {
            viewModelScope.launch {
                user = LoadingUser
                user = LoadedUser(
                    runCatching {
                        val id = repository.getUserInfo()?.id
                        Log.v("LOGOUT", "logout: " + id)
                        usersService.logout(id)
                    }
                )


            }
        }
    }




















