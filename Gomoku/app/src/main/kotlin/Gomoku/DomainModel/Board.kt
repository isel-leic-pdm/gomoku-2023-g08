package Gomoku.DomainModel

import Row
var BOARD_DIM = 15

data class BoardShow(val moves: Map<Cell, Player>)

data class Board(val moves: Map<Cell, Player>, var turn: Player?, val winner: Player? = null) {
    fun get(l: Row, c: Column) = moves[Cell(l, c)] ?: Player.EMPTY
    var lastMove: Pair<Cell, Player>? = null

    companion object {
        fun create(): Board {
            return Board(emptyMap(), Player.PLAYER_X)
        }



        fun fromString(s: String, variante: variantes, openingrule: openingrule): Board {
            if(variante == variantes.NORMAL) BOARD_DIM = 15 else BOARD_DIM = 19
            val stringaux = s.replace("\n", "")
            val boardCells = mutableMapOf<Cell, Player>()
            if(variante == variantes.OMOK) {
                for (row in 0 until BOARD_DIM) {
                    for (col in 0 until BOARD_DIM) {
                        val player = Player.fromChar(stringaux[row * BOARD_DIM + col])
                        boardCells[Cell(row, col)] = player
                    }
                }
            }
            else {
                for (row in 0 until BOARD_DIM) {
                    for (col in 0 until BOARD_DIM) {
                        val player = Player.fromChar(stringaux[row * BOARD_DIM + col])
                        boardCells[Cell(row, col)] = player
                    }
                }
            }


            return Board(boardCells, turn = Player.PLAYER_X)
        }



}
    fun BoardToString(variante: variantes? = null): String = buildString {
        if(variante == variantes.NORMAL) BOARD_DIM = 15  else BOARD_DIM = 19
        for (row in 0 until BOARD_DIM) {
            for (col in 0 until BOARD_DIM) {
                val cell = Cell(row,  col)
                println(cell)
                val player = moves[cell] ?: Player.EMPTY
                println("player: $player")
                append(player.toChar())

                // Adicione um espaço ou quebra de linha para melhorar a legibilidade

            }
            append('\n')
        }

    }

}



