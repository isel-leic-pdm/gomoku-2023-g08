package Gomoku.ReplayGames




import Gomoku.Services.ReplayGameInterface
import Gomoku.State.IdleSaveReplayGame
import Gomoku.State.LoadedSaveReplayGame
import Gomoku.State.LoadingSaveReplayGame
import Gomoku.State.loadSaveReplayGame
import android.util.Log

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class ReplayGameViewModel() : ViewModel() {
    var idGame by mutableStateOf(0)
        private set
    val idGameFlow: Int
        get() = idGame
    fun setIdGames(id: Int) {
        Log.v("SAVEGAME","id-set : "+ id.toString())
       this.idGame = id
    }
    var gamedReplay by mutableStateOf<loadSaveReplayGame>(IdleSaveReplayGame)
        private set
   fun getGameSaved(serviceuser: ReplayGameInterface): Unit {
        viewModelScope.launch {
            gamedReplay = LoadingSaveReplayGame
            gamedReplay = LoadedSaveReplayGame(
                runCatching {
                    serviceuser.fetchReplayGame(idGame)
                }

            )
    Log.v("SAVEGAME","game : "+ gamedReplay.toString())
        }
    }

}














