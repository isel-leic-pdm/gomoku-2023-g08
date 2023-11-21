package Gomoku.CreateGame

import Gomoku.DomainModel.Board
import Gomoku.DomainModel.Cell
import Gomoku.DomainModel.Game
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.toOpeningRule
import Gomoku.DomainModel.toVariante
import Gomoku.DomainModel.variantes
import Gomoku.Services.CreateGameService
import Gomoku.Services.PlayGameService
import Gomoku.State.IdleGameCreated
import Gomoku.State.IdleGameWaiting
import Gomoku.State.LoadStateGameCreated
import Gomoku.State.LoadStateGameWaiting
import Gomoku.State.LoadedGameCreated
import Gomoku.State.LoadedGameWait
import Gomoku.State.LoadingGameCreated
import Gomoku.State.LoadingGameWait
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WaitingRoomViewModel() : ViewModel() {

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
        Log.v("aaaa", "CreateGameActivity.onCreate() called ${id}, ${lobby}, ${openingRule}, ${variante}, $authToken1")
        viewModelScope.launch {
            lobby = LoadingGameWait
            lobby = LoadedGameWait(
                runCatching {
                    service.fetchCreateGame(id, openingRule, variante, getToken(id))
                })
        }
    }
    fun getToken(id: Int)=
        if(id == 1) "855039a4-226a-4ed1-ac5e-3a0814a231fc"
    else if(id==2) "c6e9a3cf-b764-44cc-884d-8950d84053d6"
    else if (id==3) "170cd3f2-8fe5-4387-8e0e-ac934db45e86"
    else "19811766-57c8-4efa-9d42-acad75806867"
fun play(service: PlayGameService, line: Int, col: Int): Unit {
        viewModelScope.launch {
            game = LoadingGameCreated
            Log.v("PLAYINGggg", "$id, $line, $col, ${getToken(id)}, ${10}")
            game = LoadedGameCreated(
                runCatching {
                    service.play(3, line, col, getToken(id), 14)
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

    fun observeGameCreated(listener: () -> Unit) {
        gameCreatedListener = listener
        checkGameCreated()
    }

    private fun checkGameCreated() {
        viewModelScope.launch {
            while (true) {
                val gameCreated = game

                    if (game != null) {
                        gameCreatedListener?.invoke()
                        break
                    }

                delay(1000) // Aguarda 1 segundo antes de verificar novamente (ajuste conforme necessário)
            }
        }
    }
}
