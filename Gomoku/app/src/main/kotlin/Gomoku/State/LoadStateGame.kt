package Gomoku.State

import Gomoku.DomainModel.Game
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.variantes
import Gomoku.Rankings.UsersRankOutput
import kotlinx.coroutines.flow.MutableStateFlow




sealed class LoadStateUser

 object IdleUser : LoadStateUser()
 object LoadingUser : LoadStateUser()

object UserSucess : LoadStateUser()
object UserFailure : LoadStateUser()
data class LoadedUser<T>(val result: Result<T>) : LoadStateUser()


sealed class LoadStateUserRank

object IdleUserRank : LoadStateUserRank()
object LoadingUserRank : LoadStateUserRank()
data class LoadedUserRank(val result: Result<List<UsersRankOutput>>) : LoadStateUserRank()

sealed class loadSaveReplayGame
object IdleSaveReplayGame : loadSaveReplayGame()
object LoadingSaveReplayGame : loadSaveReplayGame()
data class LoadedSaveReplayGame(val result: Result<List<ReplayGameModel>>) : loadSaveReplayGame()


data class ReplayGameModel(val game_id: Int,val player: Int,val variantes: variantes, val openingrule: openingrule, val  turn: Int,val  line: Int,val  col: String,val  board: String)


sealed class LoadLobby
object LobbyLoading : LoadLobby()

object LobbyEntered : LoadLobby()
object LobbyFulled : LoadLobby()
data class LoadedLobbyWaited<T>(val result: Result<T>) : LoadLobby()

sealed class LoadStateGameCreated

object IdleGameCreated : LoadStateGameCreated()
object LoadingGameCreated : LoadStateGameCreated()
object GameCreated : LoadStateGameCreated()
data class LoadedGameCreated(val result: Result<Game?>) : LoadStateGameCreated()

