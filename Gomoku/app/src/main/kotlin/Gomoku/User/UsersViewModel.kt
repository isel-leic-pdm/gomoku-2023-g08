package Gomoku.User



import Gomoku.DataStore.Domain.UserInfo
import Gomoku.DataStore.Domain.UserInfoRepository
import Gomoku.Main.LoadState
import Gomoku.Main.idle
import Gomoku.Main.loaded
import Gomoku.Services.UsersService
import Gomoku.State.IdleUser
import Gomoku.State.LoadStateUser
import Gomoku.State.LoadedUser
import Gomoku.State.LoadingUser
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
    var user by mutableStateOf<LoadStateUser>(IdleUser)
        private set
    val id by mutableStateOf(0)
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var token by mutableStateOf("")


    private var _userInfoFlow: MutableStateFlow<LoadState<UserInfo?>> = MutableStateFlow(idle())
    val userInfoFlow: Flow<LoadState<UserInfo?>>
        get() = _userInfoFlow.asStateFlow()
    fun rightUserInfo() {
        viewModelScope.launch {
            Log.v("12345", "aa" +"${id} ${username} ${password} ${token}")
            val result = runCatching { repository.updateUserInfo(UserInfo(id, username, password, token)) }
            Log.v("12345", "$result")
            _userInfoFlow.value = loaded(result.runCatching { repository.getUserInfo() })
        }
    }
    /*
    fun fetchUserInfo() {
        if (_userInfoFlow.value is Idle) {
            _userInfoFlow.value = loading()
            viewModelScope.launch {
                val result = runCatching { repository.getUserInfo() }
                _userInfoFlow.value = loaded(result)
            }
        }
    }

     */

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

    fun loginuser(usersService: UsersService): Unit {
        Log.v("USERS", username + password)
        viewModelScope.launch {
            user = LoadingUser
            user = LoadedUser(
                runCatching {
                    usersService.loginuser(username, password)

                }

            )
        }

    }

        fun logout(usersService: UsersService): Unit {
            viewModelScope.launch {
                user = LoadingUser
                user = LoadedUser(
                    runCatching {
                        usersService.logout(id = 2)
                    }
                )


            }
        }
    }




















