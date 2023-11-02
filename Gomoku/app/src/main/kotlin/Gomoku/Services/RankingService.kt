package Gomoku.Services

import Gomoku.DomainModel.Users

interface RankingService {

    suspend fun fetchRanking(): List<Users>
}