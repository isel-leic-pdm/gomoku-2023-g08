package Gomoku.CreateGame

import Gomoku.DataStore.Domain.UserInfoRepository
import Gomoku.DomainModel.Board
import Gomoku.DomainModel.Game
import Gomoku.DomainModel.Models.WaitingRoom
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
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch

class WaitingRoomViewModel(private val  repository: UserInfoRepository,/* private val gameRepository: GamesRepository*/) : ViewModel() {
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
        viewModelScope.launch {
            lobby = LoadingGameWait
            lobby = LoadedGameWait(
                runCatching {
                    val currentUser = repository.getUserInfo()
                    val waiting =  service.fetchCreateGame(currentUser?.id, openingRule, variante, currentUser?.token ?: "")
                    if(waiting.playera != null && waiting.playerb != null){
                        val game = service.getGame(waiting.playera, waiting.playerb!!)
                        Log.v("CREATEGAME", "createGame = $game")
                        game
                        return@runCatching

                    }
                    else {
                        awaitForGameCreated(service,waiting)
                    }

                }
            )
        }
    }
    fun awaitForGameCreated(service: CreateGameService, waitingRoom: WaitingRoom): Game? {
        viewModelScope.launch {
            while (waitingRoom.playerb == null){

            }


        }
        return null
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
