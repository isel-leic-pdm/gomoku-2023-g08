package Gomoku.ReplayGames




import Gomoku.DataStore.Domain.GameInfoRepository
import Gomoku.DataStore.Domain.ReplayInfoRepository
import Gomoku.Services.PlayGameService
import Gomoku.Services.ReplayGameService
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
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min


class ReplayGameViewModel(val service: ReplayGameService,val  repository: ReplayInfoRepository) : ViewModel() {
    var idGame by mutableStateOf(0)
        private set

    var sizeMoves by mutableStateOf(0)
    var moveIndex by mutableStateOf(0)
    private var totalMoves by mutableStateOf(0)

    // Function to increment the moveIndex
    fun incrementMoveIndex() {
        if (moveIndex < sizeMoves - 1) {
            moveIndex += 1
        }
        else {
            moveIndex = 0
        }
    }

    // Function to decrement the moveIndex
    fun decrementMoveIndex() {
        if (moveIndex > 0) {
            moveIndex -= 1
        }
        else {
            moveIndex = sizeMoves - 1
        }

    }
    fun setIdGames(id: Int) {

       this.idGame = id
    }
    var gamedReplay by mutableStateOf<loadSaveReplayGame>(IdleSaveReplayGame)
        private set


  fun getGameSaved(): Unit {
      viewModelScope.launch {
          gamedReplay = LoadingSaveReplayGame
          try {
              repository.rightReplayId(idGame)
              val result = runCatching {
                  service.fetchReplayGame(repository.getReplayId())
              }
              Log.v("RESULt", "result = ${result.getOrNull()?.size}")
              gamedReplay = LoadedSaveReplayGame(result)
                sizeMoves = result.getOrNull()?.size ?: 0
              return@launch
          } catch (e: Exception) {
              // Handle errors here, if needed
            //  gamedReplay.value = ErrorSaveReplayGame(e.message)
          }
      }
  }

    companion object {
        fun factory(service: ReplayGameService, repository: ReplayInfoRepository) = viewModelFactory {
            initializer { ReplayGameViewModel(service, repository) }
        }
    }

}














