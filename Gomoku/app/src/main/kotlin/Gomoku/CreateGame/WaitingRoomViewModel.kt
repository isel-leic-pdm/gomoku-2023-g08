package Gomoku.CreateGame

import Gomoku.DataStore.Domain.UserInfoRepository
import Gomoku.DomainModel.Board
import Gomoku.DomainModel.Cell
import Gomoku.DomainModel.Game
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.toOpeningRule
import Gomoku.DomainModel.toVariante
import Gomoku.DomainModel.variantes
import Gomoku.Services.CreateGameService
import Gomoku.Services.PlayGameService
import Gomoku.Services.UsersService
import Gomoku.State.IdleGameCreated
import Gomoku.State.IdleGameWaiting
import Gomoku.State.LoadStateGameCreated
import Gomoku.State.LoadStateGameWaiting
import Gomoku.State.LoadedGameCreated
import Gomoku.State.LoadedGameWait
import Gomoku.State.LoadingGameCreated
import Gomoku.State.LoadingGameWait
import Gomoku.User.UsersViewModel
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WaitingRoomViewModel( repository: UserInfoRepository) : ViewModel() {
    val usersViewModel by lazy { UsersViewModel(repository) }

    var lobby by mutableStateOf<LoadStateGameWaiting>(IdleGameWaiting)
        private set
    var game by mutableStateOf<LoadStateGameCreated>(IdleGameCreated)
        private set

    val gamePlayed: Game? get() = (game as? LoadedGameCreated)?.result?.getOrNull()
    val currentBoard : Board? get() = gamePlayed?.board ?: null


    var id: Int by mutableStateOf(0)
        private set

    var openingRule: openingrule by mutableStateOf(openingrule.NORMAL)
        private set

    var variante: variantes by mutableStateOf(variantes.NORMAL)
        private set

    fun createGame(service: CreateGameService): Unit {
        Log.v("aaaa", "CreateGameActivity.onCreate() called ${id}, ${lobby}, ${openingRule}, ${variante}}")
        viewModelScope.launch {
            lobby = LoadingGameWait
            lobby = LoadedGameWait(
                runCatching {
                    Log.v("aaaa", "CreateGameActivity.onCreate() called ${usersViewModel.id}, ${lobby}, ${openingRule}, ${variante}, ${usersViewModel.token}")
                    service.fetchCreateGame(usersViewModel.id, openingRule, variante, usersViewModel.token)
                })
        }
    }

fun play(service: PlayGameService, line: Int, col: Int, users: UsersService): Unit {
        viewModelScope.launch {
            game = LoadingGameCreated
            game = LoadedGameCreated(
                runCatching {
                    val token =   users.getAuthToken(id)
                    service.play(3, line, col, token, 14)
                })
        }
    }
    fun getGame(service: CreateGameService, id:Int): Unit {
        viewModelScope.launch {
            game = LoadingGameCreated
            game = LoadedGameCreated(
                runCatching {
                  service.getGame(id)
                })
        }
    }

    fun setIDS(id: Int) {
        this.id = id
    }

    fun SetOpeningRules(openingRule: String) {
        this.openingRule = openingRule.toOpeningRule()
    }

    fun SetVariantes(variante: String) {
        this.variante = variante.toVariante()
    }

    private var gameCreatedListener: (() -> Unit)? = null

    companion object {
        fun factory(repository: UserInfoRepository) = viewModelFactory {
            initializer { WaitingRoomViewModel(repository) }
        }
    }



}
