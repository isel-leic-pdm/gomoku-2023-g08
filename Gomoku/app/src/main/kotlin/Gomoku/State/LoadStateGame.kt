package Gomoku.State

import Gomoku.DomainModel.Board
import Gomoku.DomainModel.Game
import Gomoku.DomainModel.Models.WaitingRoom
import Gomoku.DomainModel.Users
import Gomoku.Rankings.UsersRankOutput


sealed class LoadStateGame

 object IdleGame : LoadStateGame()
 object LoadingGame : LoadStateGame()
data class LoadedGame(val result: Result<Game>) : LoadStateGame()


sealed class LoadStateUser

 object IdleUser : LoadStateUser()
 object LoadingUser : LoadStateUser()
data class LoadedUser<T>(val result: Result<T>) : LoadStateUser()


sealed class LoadStateUserRank

object IdleUserRank : LoadStateUserRank()
object LoadingUserRank : LoadStateUserRank()
data class LoadedUserRank(val result: Result<List<UsersRankOutput>>) : LoadStateUserRank()

sealed class loadSaveReplayGame
object IdleSaveReplayGame : loadSaveReplayGame()
object LoadingSaveReplayGame : loadSaveReplayGame()
data class LoadedSaveReplayGame(val result: Result<List<ReplayGameModel>>) : loadSaveReplayGame()


data class ReplayGameModel(val game_id: Int,val player: Int,val  turn: Int,val  line: Int,val  col: String,val  board: String)


sealed class LoadStateGameWaiting

object IdleGameWaiting : LoadStateGameWaiting()
object LoadingGameWait : LoadStateGameWaiting()
data class LoadedGameWait(val result: Result<WaitingRoom>) : LoadStateGameWaiting()

sealed class LoadStateGameCreated

object IdleGameCreated : LoadStateGameCreated()
object LoadingGameCreated : LoadStateGameCreated()
data class LoadedGameCreated(val result: Result<Game?>) : LoadStateGameCreated()
