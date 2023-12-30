package Gomoku.ReplayGames




import Gomoku.DataStore.Domain.ReplayInfoRepository
import Gomoku.DataStore.Domain.UserInfoRepository
import Gomoku.Services.ReplayGameService
import Gomoku.State.IdleListIds
import Gomoku.State.IdleSaveReplayGame
import Gomoku.State.LoadedListIds
import Gomoku.State.LoadedSaveReplayGame
import Gomoku.State.LoadingListIds
import Gomoku.State.LoadingSaveReplayGame
import Gomoku.State.loadListIds

import Gomoku.State.loadSaveReplayGame
import android.util.Log

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch


class ReplayGameViewModel(val service: ReplayGameService,val  repository: ReplayInfoRepository, val userRepo: UserInfoRepository) : ViewModel() {
    var idGame by mutableStateOf(0)
        private set

    var sizeMoves by mutableStateOf(0)
    var moveIndex by mutableStateOf(0)
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
    var ListIds by mutableStateOf<loadListIds>(IdleListIds)
        private set


    fun fetchListIds(): Unit {
        viewModelScope.launch {
            ListIds = LoadingListIds
            try {
                val idUser = userRepo.getUserInfo()?.id
                Log.v("USERID", "id = ${idUser}")
                val result = runCatching {
                    service.fetchListIds(idUser)
                }
                ListIds = LoadedListIds(result)

                return@launch
            } catch (e: Exception) {
                // Handle errors here, if needed
                //  gamedReplay.value = ErrorSaveReplayGame(e.message)
            }
        }
    }
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
        fun factory(service: ReplayGameService, repository: ReplayInfoRepository, userRepo: UserInfoRepository) = viewModelFactory {
            initializer { ReplayGameViewModel(service, repository, userRepo) }
        }
    }

}














