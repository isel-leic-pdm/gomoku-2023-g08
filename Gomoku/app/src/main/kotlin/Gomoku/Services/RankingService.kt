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


   suspend fun getGame(id: Int): Game?
    suspend fun fetchCreateGame(id: Int?, openingrule: openingrule?, variantes: variantes?, authToken: String): WaitingRoom
}

interface ReplayGameInterface {

    suspend fun fetchReplayGame(id: Int): List<ReplayGameModel>
}

