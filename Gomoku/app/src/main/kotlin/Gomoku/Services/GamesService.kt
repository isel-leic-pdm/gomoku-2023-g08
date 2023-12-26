package Gomoku.Services

import Gomoku.DomainModel.Game
import Gomoku.DomainModel.Models.WaitingRoom
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.variantes


interface CreateGameService {


    suspend fun fetchCreateGame(id: Int?, openingrule: openingrule?, variantes: variantes, authToken: String): WaitingRoom

    suspend fun fetchWaitingRoom(idplayer: Int?): WaitingRoom?
    suspend fun getGameString(id: Int): Game?


}
interface PlayGameService {

    suspend fun play(id: Int?, line: Int, col: Int, authToken: String?, idGame: Int): Game?
    suspend fun getGame(gameID: Int?): Game?
}

class FetchGameException(message: String, cause: Throwable? = null)
    : Exception(message, cause)
class FetchUser1Exception(message: String, cause: Throwable? = null)
    : Exception(message, cause)