package Gomoku.State

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
data class LoadedUser(val result: Result<Users>) : LoadStateUser()


sealed class LoadStateUserRank

object IdleUserRank : LoadStateUserRank()
object LoadingUserRank : LoadStateUserRank()
data class LoadedUserRank(val result: Result<List<UsersRankOutput>>) : LoadStateUserRank()


sealed class LoadStateGameWaiting

object IdleGameWaiting : LoadStateGameWaiting()
object LoadingGameWait : LoadStateGameWaiting()
data class LoadedGameWait(val result: Result<WaitingRoom>) : LoadStateGameWaiting()


