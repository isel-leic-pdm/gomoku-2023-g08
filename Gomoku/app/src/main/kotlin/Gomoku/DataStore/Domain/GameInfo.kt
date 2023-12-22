package Gomoku.DataStore.Domain

import Gomoku.DomainModel.Board
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.variantes
import java.net.URL


/**
 * Represents the user information, to be used while he is in the lobby waiting for
 * an opponent and during the game. Initialization parameters must be valid as specified by the
 * [validateUserInfoParts] function.
 * @property [id] the user's nick name.
 * @property [password] the user's moto, if he has one.
 */
data class GameInfo(val id: Int?, val board: Board, val player1: Int?, val player2: Int?, val variante: variantes?, val openingrule: openingrule, val winner: Int? = null, val turn: Int?, val url: URL? = null)

