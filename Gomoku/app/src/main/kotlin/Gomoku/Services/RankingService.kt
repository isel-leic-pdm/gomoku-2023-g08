package Gomoku.Services

import Gomoku.DomainModel.Models.WaitingRoom
import Gomoku.DomainModel.Users
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.variantes
import Gomoku.Rankings.UsersRankOutput

interface RankingService {

    suspend fun fetchRanking(): MutableList<UsersRankOutput>
}

interface CreateGameService {

    suspend fun fetchCreateGame(id:Int, openingrule: openingrule, variantes: variantes): WaitingRoom
}