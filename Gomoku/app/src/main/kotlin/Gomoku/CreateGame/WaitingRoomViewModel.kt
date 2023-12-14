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
import Gomoku.State.GameCreated
import Gomoku.State.LobbyLoading
import Gomoku.State.IdleGameCreated
import Gomoku.State.LoadStateGameCreated
import Gomoku.State.LoadLobby
import Gomoku.State.LoadedGameCreated
import Gomoku.State.LoadedLobbyWaited
import Gomoku.State.LoadingGameCreated
import Gomoku.State.LobbyFulled
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WaitingRoomViewModel(private val  repository: UserInfoRepository,/* private val gameRepository: GamesRepository*/) : ViewModel() {

    var _waitingRoom : MutableStateFlow<LoadLobby> = MutableStateFlow(LobbyLoading)
    var waitingRoom get() = _waitingRoom.value
        set(value) {
            _waitingRoom.value = value
        }
    var _game : MutableStateFlow<LoadStateGameCreated> = MutableStateFlow(IdleGameCreated)

    var game get() = _game.value
        set(value) {
            _game.value = value
        }
    val currentGame get() = game as? LoadedGameCreated
    var gametest : Game? = null




    var id: Int by mutableStateOf(0)
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
                    var waiting = service.fetchCreateGame(currentUser?.id, openingRule, variante,currentUser?.token ?: "")
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
                                    if(waiting.gameID != null){
                                        waitingRoom = LobbyFulled
                                        game = LoadedGameCreated(
                                          runCatching {
                                              val getGame =  service.getGame(waiting.gameID)
                                              gametest= getGame
                                              getGame

                                          }


                                      )
                                        Log.v("MATCHMAKING", "$game")


                                    }
                                }
                            }
                        }
                    }
                }
            )
        }
    }

fun play(service: PlayGameService, line: Int, col: Int, users: UsersService): Unit {
        viewModelScope.launch {
            game = LoadingGameCreated
            game = LoadedGameCreated(
                runCatching {
                    // consoante os dois jogadores , ir bsucar o ID do game
                    val gameID = service.getGame(id)
                    val currentUser = repository.getUserInfo()
                  //  val userUpdated = repository.updateUserInfo(currentUser?.copy(gamePlaying = ))
                    Log.v("PLAY, ", "play = $currentUser")
                    val currentGame = repository.getCurrentGame()
                    Log.v("PLAY, ", "play = $currentUser")

                    Log.v("PLAY, ", "play = ${currentUser?.id}, $line, $col, ${currentUser?.token}, $currentGame")
                    service.play(currentUser?.id, line, col, currentUser?.token, currentGame)
                })
        }
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
