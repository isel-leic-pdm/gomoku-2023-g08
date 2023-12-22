package Gomoku.DomainModel

import Row
var BOARD_DIM = 15



data class Board(val moves: Map<Cell, Player>, var turn: Player?, val winner: Player? = null) {
    fun get(l: Row, c: Column) = moves[Cell(l, c)] ?: Player.EMPTY
    var lastMove: Pair<Cell, Player>? = null

    companion object {
        fun create(): Board {
            return Board(emptyMap(), Player.PLAYER_X)
        }


    }
}



