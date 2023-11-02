package Gomoku.NewGame



import Gomoku.Services.GamesService
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


class NewGameModel() : ViewModel() {


    var id by mutableStateOf<LoadStateUser>(IdleUser)
        private set
    var openingRule : String =""
    var variante : String =""
/*
    fun createGame(variante : String, openingRule: String, idGame: GamesService): Unit {
        viewModelScope.launch {
            id = LoadingUser
            id = LoadedUser(
                runCatching {
                        idGame.fetchGame()

                }
            ) }
    }*/
}














