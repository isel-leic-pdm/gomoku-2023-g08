package Gomoku.ReplayGames




import Gomoku.Services.RankingService
import Gomoku.Services.ReplayGameInterface
import Gomoku.State.IdleSaveReplayGame
import Gomoku.State.IdleUserRank
import Gomoku.State.LoadStateUserRank
import Gomoku.State.LoadedSaveReplayGame
import Gomoku.State.LoadedUserRank
import Gomoku.State.LoadingSaveReplayGame
import Gomoku.State.LoadingUserRank
import Gomoku.State.loadSaveReplayGame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class ReplayGameViewModel() : ViewModel() {
    var idGame = 1
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

        }
    }

}














