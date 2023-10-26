package Gomoku.Services

import Gomoku.DomainModel.Game


interface GamesService {

    suspend fun fetchGame(): Game
}

class FetchGameException(message: String, cause: Throwable? = null)
    : Exception(message, cause)

