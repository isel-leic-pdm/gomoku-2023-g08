package Gomoku.DataStore.Domain

import Gomoku.DomainModel.Game

interface GameInfoRepository {

    suspend fun getCurrentGame(): Game
    suspend fun rightCurrentGame(Game: Game)

}