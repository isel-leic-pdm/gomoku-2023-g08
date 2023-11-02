package Gomoku.State

import Gomoku.DomainModel.Game
import Gomoku.DomainModel.Users


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
data class LoadedUserRank(val result: Result<List<Users>>) : LoadStateUserRank()