package Gomoku.Services

import Gomoku.DomainModel.Game
import Gomoku.DomainModel.Models.WaitingRoom
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.variantes
import Gomoku.Rankings.UsersRankOutput
import Gomoku.State.ReplayGameModel

interface RankingService {

    suspend fun fetchRanking(): List<UsersRankOutput>
}

interface CreateGameService {


    suspend fun fetchCreateGame(id: Int?, openingrule: openingrule?, variantes: variantes, authToken: String): WaitingRoom
   suspend fun getGame(id: Int): Game?

}
interface PlayGameService {

    suspend fun play(id: Int, line: Int, col: Int, authToken: String, idGame: Int): Game?
}

interface ReplayGameInterface {

    suspend fun fetchReplayGame(id: Int): List<ReplayGameModel>
}

