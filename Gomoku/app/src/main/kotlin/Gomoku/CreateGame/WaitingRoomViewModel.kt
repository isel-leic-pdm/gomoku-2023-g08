package Gomoku.CreateGame

import Gomoku.DataStore.Domain.UserInfoRepository
import Gomoku.DomainModel.Game
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.toOpeningRule
import Gomoku.DomainModel.toVariante
import Gomoku.DomainModel.variantes
import Gomoku.Services.CreateGameService
import Gomoku.Services.PlayGameService
import Gomoku.Services.UsersService
import Gomoku.State.LobbyLoading
import Gomoku.State.IdleGameCreated
import Gomoku.State.LoadStateGameCreated
import Gomoku.State.LoadLobby
import Gomoku.State.LoadedGameCreated
import Gomoku.State.LoadedLobbyWaited
import Gomoku.State.LoadingGameCreated
import Gomoku.State.LobbyFulled
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class WaitingRoomViewModel(private val  repository: UserInfoRepository,private val service: CreateGameService, private val playGameService: PlayGameService) : ViewModel() {

    var _waitingRoom : MutableStateFlow<LoadLobby> = MutableStateFlow(LobbyLoading)
    var waitingRoom get() = _waitingRoom.value
        set(value) {
            _waitingRoom.value = value
        }
    var _game : MutableStateFlow<LoadStateGameCreated> = MutableStateFlow(IdleGameCreated)
    var gameMT : MutableState<LoadStateGameCreated> = mutableStateOf(IdleGameCreated)

    var game get() = _game.value
        set(value) {
            _game.value = value
        }

    var id: Int? by mutableStateOf(null)
        private set

    var openingRule: openingrule by mutableStateOf(openingrule.NORMAL)
        private set

    var variante: variantes by mutableStateOf(variantes.NORMAL)
        private set


    fun createGame(service: CreateGameService): Unit {
        viewModelScope.launch {
            waitingRoom = LoadedLobbyWaited(
                runCatching {
                    val currentUser = repository.getUserInfo()
                    var waiting = service.fetchCreateGame(2, openingRule, variante,currentUser?.token ?: "")
                    Log.v("MATCHMAKING", "ESTOU AQUI  depois de ter feito um fetch game = $waiting, ainda nao entrei no if ")
                    if(waiting.gameID != null){
                        Log.v("MATCHMAKING", "ESTOU AQUI  depois de ter feito um fetch game = $waiting")
                    waitingRoom = LobbyFulled
                    }
                    else {
                        if(waiting.gameID == null){
                            Log.v("MATCHMAKING", "ESTOU AQUI  depois de ter feito um fetch game = $waiting, GAMEID = ${waiting.gameID}")
                            viewModelScope.launch {
                                while(waiting.gameID == null){
                                    Log.v("MATCHMAKING", "ESTOU PRESO NO WHILE = $waiting")
                                    delay(1000)
                                    val updatedWaiting = service.fetchCreateGame(currentUser?.id, openingRule, variante, currentUser?.token ?: "")
                                    waiting = updatedWaiting
                                    val gameID = waiting.gameID
                                    if(gameID != null){
                                        // repository.clearCurrentGame()
                                        repository.updateCurrentGame(gameID)
                                        while(repository.getCurrentGame() != gameID){
                                            delay(1000)
                                        }
                                        waitingRoom = LobbyFulled
                                        async { getGame()  }.await()
                                        if(gameMT.value is LoadedGameCreated){
                                            val loadedGame = gameMT.value as LoadedGameCreated
                                            val game = loadedGame.result.getOrNull()
                                            if(game?.turn != currentUser?.id){
                                                startPolling()
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }

                }
            )
        }
    }
    fun startPolling() {
        viewModelScope.launch {
            val currentUser = repository.getUserInfo()
            var currentGame = repository.getCurrentGame()
            var game= service.getGame(currentGame)
            Log.v("PLAYGAME", "Polling game")
            while (game?.turn != currentUser?.id) {
                game = service.getGame(currentGame)
                if(game?.turn == currentUser?.id){
                    Log.v("POLLING", "GAMEMT ANTES DO POLLING = ${gameMT.value}")
                    gameMT.value = LoadedGameCreated(runCatching { game })
                    Log.v("POLLING", "GAMEMT DEPOIS DO POLLING = ${gameMT.value}")
                    break
                }
                delay(1000)
            }
        }
    }


    fun getGame() {
        viewModelScope.launch {
            gameMT.value = LoadingGameCreated
            Log.v("PLAYGAME", "getGame = ${repository.getCurrentGame()}")
            gameMT.value = LoadedGameCreated(
                runCatching { service.getGame(repository.getCurrentGame()) })}
    }

    fun getGameG(): Game? {
        var game : Game? = null
        viewModelScope.launch {
            gameMT.value = LoadingGameCreated
            val gameID = repository.getCurrentGame()
            Log.v("PLAYGAME", "getGame = ${repository.getCurrentGame()}")
            gameMT.value = LoadedGameCreated(
                runCatching {
                 game =  service.getGame(gameID)
                    Log.v("PLAYGAME", "game after Fetch = $game")
                    game
                })
        }
        if(gameMT.value is LoadedGameCreated){
           val loadedGame = gameMT.value as LoadedGameCreated
            game = loadedGame.result.getOrNull()
        }
    //    Log.v("PLAYGAME", "game after Fetch = $game")
     //   Log.v("PLAYGAME", "game turn  after Fetch = ${game?.turn}")
     //   Log.v("PLAYGAME", "gameMT after Fetch = ${gameMT.value}")
        return game
    }

fun play(line: Int, col: Int): Unit {
    viewModelScope.launch {
        val currentGameBoard = getGameG()

        Log.v("PLAYGAME", "currentGameBoard = $currentGameBoard , turn = ${currentGameBoard?.turn}")
        val currentUser = repository.getUserInfo()
        Log.v("PLAYGAME", "currentGameBoard = $currentGameBoard , turn = ${currentGameBoard?.turn}, currentUser = ${currentUser?.id}")
        if(currentGameBoard?.turn == currentUser?.id){
            Log.v("PLAYGAME", "mesma turn = ${repository.getCurrentGame()}, ${currentUser?.id}")
            gameMT.value = LoadingGameCreated
            gameMT.value = LoadedGameCreated(
                runCatching {
                    val currentGame = repository.getCurrentGame()
                   playGameService.play(currentUser?.id, line, col, currentUser?.token, currentGame)

                })
            startPolling()
        }

    }
    }

    fun SetOpeningRules(openingRule: String) {
        this.openingRule = openingRule.toOpeningRule()
    }

    fun SetVariantes(variante: String) {
        this.variante = variante.toVariante()
    }

    companion object {
        fun factory(repository: UserInfoRepository, service: CreateGameService, playGameService: PlayGameService) = viewModelFactory {
            initializer { WaitingRoomViewModel(repository,service, playGameService ) }
        }
    }



}
