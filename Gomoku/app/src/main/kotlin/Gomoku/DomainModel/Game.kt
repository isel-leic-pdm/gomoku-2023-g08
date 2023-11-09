package Gomoku.DomainModel

import Row
import java.net.URL

enum class variantes {
   NORMAL,
    OMOK,
}
fun String.toVariante() = when (this) {
    "NORMAL" -> variantes.NORMAL
    "OMOK" -> variantes.OMOK
    else -> throw IllegalArgumentException("Invalid variant.")
}
enum class openingrule {
    NORMAL,
    PRO,

}
fun String.toOpeningRule() = when (this) {
    "NORMAL" -> openingrule.NORMAL
    "PRO" -> openingrule.PRO
    else -> throw IllegalArgumentException("Invalid opening rule.")
}
data class Game(val board: Board, val player1: Int?, val player2: Int?,val variante: variantes?, val openingrule: openingrule,  val winner: Int? = null, val turn: Int?, val url :URL? = null) {
    fun get(l: Row, c: Column) = board.get(l, c)
    companion object {

    }

}


typealias moves = Map<Cell,Player>

const val GAME_SIZE = 225
const val OMOK_DIM = 19

enum class Player(val char: Char)  {
    CHANGELINE('\n'),
    SPACING(' '),
    EMPTY('-'),
    PLAYER_X('X'),
    PLAYER_O('O');

    fun other() = when (this) {
        PLAYER_X -> PLAYER_O
        PLAYER_O -> PLAYER_X
        else -> throw IllegalArgumentException("Empty player has no other player.")
    }

    fun toChar() = when (this) {
        EMPTY -> '-'
        PLAYER_X -> 'X'
        PLAYER_O -> 'O'
        SPACING -> ' '
      CHANGELINE -> '\n'

    }



    companion object {
        fun fromChar(c: Char) = when (c) {
            '-' -> EMPTY
            'X' -> PLAYER_X
            'O' -> PLAYER_O
            '\n' -> CHANGELINE
            else -> throw IllegalArgumentException("Invalid character for player.")
        }

    }
}
fun randomPlayer(player1: Int?, player2: Int?): Int {
    if(player1 == null || player2 == null) throw Exception("Players not found")
    val random = (0..1).random()
    return if (random == 0) {
        player1
    } else {
        player2
    }
}




























