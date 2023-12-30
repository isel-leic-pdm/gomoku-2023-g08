package Gomoku.Services

import Gomoku.DomainModel.GameReplayShowModel
import Gomoku.Rankings.UsersRankOutput

interface RankingService {

    suspend fun fetchRanking(): List<UsersRankOutput>
}



interface ReplayGameService {

    suspend fun fetchReplayGame(id: Int): List<GameReplayShowModel>
}

