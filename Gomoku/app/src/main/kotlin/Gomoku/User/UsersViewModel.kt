package Gomoku.User



import Gomoku.Services.UsersService
import Gomoku.State.IdleUser
import Gomoku.State.LoadStateUser
import Gomoku.State.LoadedUser
import Gomoku.State.LoadingUser
import android.util.Log

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import isel.pdm.demos.tictactoe.ui.preferences.DataStoreKeys
import kotlinx.coroutines.launch



class UsersViewModel(/*private val dataStore: DataStore<Preferences>*/) : ViewModel() {


    var user by mutableStateOf<LoadStateUser>(IdleUser)
        private set
    var username by mutableStateOf("")
    var password by mutableStateOf("")
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
                        usersService.logout(2)

                    }
                )
            }
        }
    }

     /*
    fun loginuser(username: String, password: String, usersService: UsersService) {
        viewModelScope.launch {
            user = LoadingUser
            val result = runCatching {
                usersService.loginuser(username, password)
            }

            user = LoadedUser(result)

            // Se o login for bem-sucedido, salve os dados no DataStore
            if (result.isSuccess) {
                val userId =  usersService.getUserID(username, password)
                val authToken = usersService.getAuthToken(userId)

                saveUserData(userId, authToken)
            }
        }
    }


      */
    /*
    suspend fun saveUserData(userId1: Int, authToken1: String) {
        dataStore.edit { preferences ->
            preferences[DataStoreKeys.USER_ID] = userId1.toString()
            preferences[DataStoreKeys.AUTH_TOKEN] = authToken1
        }
    }

*/

















