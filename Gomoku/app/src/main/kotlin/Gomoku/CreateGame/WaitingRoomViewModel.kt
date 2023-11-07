package Gomoku.CreateGame

import Gomoku.DomainModel.Models.WaitingRoom
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.variantes
import Gomoku.Services.CreateGameService
import Gomoku.State.IdleGameWaiting
import Gomoku.State.IdleUser
import Gomoku.State.LoadStateGameWaiting
import Gomoku.State.LoadStateUser
import Gomoku.State.LoadedGameWait
import Gomoku.State.LoadingGameWait
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WaitingRoomViewModel() : ViewModel() {
    var waitGame by mutableStateOf<LoadStateGameWaiting>(IdleGameWaiting)
        private set
    var id by mutableStateOf<LoadStateUser>(IdleUser)
        private set
    var openingRule : String =""
    var variante : String =""
    fun createGame(service: CreateGameService, id: Int, openingRule: openingrule, variantes: variantes): WaitingRoom? {
        Log.v("bbb", "CreateGameActivity.onCreate() called ${id}, ${openingRule}, ${variantes}")
        viewModelScope.launch {
            waitGame = LoadingGameWait
            waitGame = LoadedGameWait(
                runCatching {
                service.fetchCreateGame(id, openingRule, variantes)
                }
            )
        }

return null
       }

}
