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
   var gameMT : LoadStateGameCreated by mutableStateOf(IdleGameCreated)
       private set


    var gamecheck : MutableState<Game?> = mutableStateOf(null)
    var openingRule: openingrule by mutableStateOf(openingrule.NORMAL)
        private set

    var variante: variantes by mutableStateOf(variantes.NORMAL)
        private set


    @SuppressLint("SuspiciousIndentation")
    fun createGame(service: CreateGameService): Unit {
        viewModelScope.launch {
            waitingRoom = LoadedLobbyWaited(
                runCatching {
                    val currentUser = repository.getUserInfo()
                    var waiting = service.fetchCreateGame(currentUser?.id, openingRule, variante,currentUser?.token ?: "")
                    if(waiting.gameID != null){
                        val currentGameID = waiting.gameID ?: 0
                        repository.updateCurrentGame(currentGameID)
                        while(repository.getCurrentGame() != waiting.gameID){ delay(1000) }
                        waitingRoom = LobbyFulled
                       val g =  async { getGameG()  }.await()
                        Log.v("ONDE ESTOU", "ESTOU AQUI-0, gameCheck = ${gamecheck.value?.turn}, currentUser = ${currentUser?.id}")
                        if(gamecheck.value?.turn != currentUser?.id){
                            Log.v("ONDE ESTOU", "ESTOU AQUI-1")
                            startPolling()
                        }
                        if(gameMT is LoadedGameCreated){
                            val loadedGame = gameMT as LoadedGameCreated
                            val game = loadedGame.result.getOrNull()
                            Log.v("POLLIING", "ESTOU AQUI se nao for a minha vez")
                            if(game?.turn != currentUser?.id){
                                Log.v("ONDE ESTOU", "ESTOU AQUI-2")
                                startPolling()
                            }
                        }
                    }
                    else {
                        if(waiting.gameID == null){
                            viewModelScope.launch {
                                while(waiting.gameID == null){
                                    Log.v("MATCHMAKING", "ESTOU PRESO NO WHILE = $waiting")
                                    delay(1000)
                                    val updatedWaiting = service.fetchCreateGame(currentUser?.id, openingRule, variante, currentUser?.token ?: "")
                                    waiting = updatedWaiting
                                    val gameID = waiting.gameID
                                    if(gameID != null){
                                        repository.updateCurrentGame(gameID)
                                        while(repository.getCurrentGame() != gameID){ delay(1000) } //
                                        waitingRoom = LobbyFulled
                                       async { getGame()  }.await()
                                        while (gameMT !is LoadedGameCreated){ delay(1000) } //
                                        // buscar jogo atual e verificar a vez
                                        if(gameMT is LoadedGameCreated) {
                                            val loadedGame = gameMT as LoadedGameCreated
                                            val game = loadedGame.result.getOrNull()
                                            if (game?.turn != currentUser?.id) {
                                                Log.v("ONDE ESTOU", "ESTOU AQUI-4")
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

    fun play(line: Int, col: Int): Unit {
        viewModelScope.launch {
            val currentGameBoard = getGameG()
            Log.v("PLAYGAME", "currentGameBoard = $currentGameBoard , turn = ${currentGameBoard?.turn}")
            val currentUser = repository.getUserInfo()
            Log.v("PLAYGAME", "currentGameBoard = $currentGameBoard , turn = ${currentGameBoard?.turn}, currentUser = ${currentUser?.id}")
            if(currentGameBoard?.turn == currentUser?.id){
                Log.v("PLAYGAME", "mesma turn = ${repository.getCurrentGame()}, ${currentUser?.id}")
                gameMT = LoadingGameCreated
                gameMT = LoadedGameCreated(
                    runCatching {
                        val currentGame = repository.getCurrentGame()
                        playGameService.play(currentUser?.id, line, col, currentUser?.token, currentGame)

                    })
                startPolling()
            } else {
                val gameNotUpdated = service.getGameString(repository.getCurrentGame())
                gameMT = LoadedGameCreated(
                    runCatching {
                        gameNotUpdated
                    }
                )
            }

        }
    }
    @SuppressLint("SuspiciousIndentation")
    fun startPolling() {
        viewModelScope.launch {
            val currentUser = repository.getUserInfo()
            var currentGame = repository.getCurrentGame()
            var game= service.getGameString(currentGame)
            Log.v("PLAYGAME", "Polling game")
            while (game?.turn != currentUser?.id && game?.winner == 0) {
                game = service.getGameString(currentGame)
                if(game?.turn == currentUser?.id){
                    Log.v("POLLING", "GAMEMT ANTES DO POLLING = ${gameMT}")
                    gameMT = LoadedGameCreated(runCatching { game })

                    Log.v("POLLING", "GAMEMT DEPOIS DO POLLING = ${gameMT}")
                    break
                }
                    // toUpdate Winner
                if(game?.winner != 0){
                    Log.v("POLLING", "GAMEMT ANTES DE WINNer = ${gameMT}")
                    gameMT = LoadedGameCreated(runCatching { game })

                    Log.v("POLLING", "GAMEMT DEPOIS DO WINNER = ${gameMT}")
                    break
                }
                delay(1000)
            }

                   }
    }

    fun getGame() {
        viewModelScope.launch {
            gameMT = LoadingGameCreated
            Log.v("PLAYGAME", "getGame = ${repository.getCurrentGame()}")
            gameMT = LoadedGameCreated(
                runCatching { service.getGameString(repository.getCurrentGame()) })}
    }

    fun getGameG(): Game? {
        var game : Game? = null
        viewModelScope.launch {
            gameMT = LoadingGameCreated
            val gameID = repository.getCurrentGame()
            Log.v("PLAYGAME", "getGame = ${repository.getCurrentGame()}")
            gameMT = LoadedGameCreated(
                runCatching {
                 game =  service.getGameString(gameID)
                    game
                })
        }
        if(gameMT is LoadedGameCreated){
           val loadedGame = gameMT as LoadedGameCreated
            game = loadedGame.result.getOrNull()
        }
        return game
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
