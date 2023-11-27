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
    fun isWin(moves: Moves): Boolean {
        val lastMove = this.lastMove ?: return false
        val (lastCell, player) = lastMove
        val directions = listOf(
            Pair(1, 0),  // horizontal
            Pair(0, 1),  // vertical
            Pair(1, 1),  // diagonal (top-left to bottom-right)
            Pair(1, -1)  // diagonal (top-right to bottom-left)
        )

        for ((dx, dy) in directions) {
            var count = 1

            // Check one direction
            for (i in 1 until 5) {
                val row = lastCell.rowIndex + i * dx
                val col = lastCell.colIndex + i * dy

                if (row < 0 || col < 0 || row >= BOARD_DIM || col >= BOARD_DIM) {
                    break
                }

                val cell = Cell(row, col)

                if (moves[cell] == player) {
                    count++
                    if (count == 5) {
                        return true
                    }
                } else {
                    break
                }
            }

            // Check the opposite direction
            for (i in 1 until 5) {
                val row = lastCell.rowIndex - i * dx
                val col = lastCell.colIndex - i * dy

                if (row < 0 || col < 0 || row >= BOARD_DIM || col >= BOARD_DIM) {
                    break
                }

                val cell = Cell(row, col)

                if (moves[cell] == player) {
                    count++
                    if (count == 5) {
                        return true
                    }
                } else {
                    break
                }
            }
        }

        return false

    }

    fun isWinOMOK(moves: Moves): Boolean {
        val lastMove = this.lastMove ?: return false
        val (lastCell, player) = lastMove
        val directions = listOf(
            Pair(1, 0),  // horizontal
            Pair(0, 1),  // vertical
            Pair(1, 1),  // diagonal (top-left to bottom-right)
            Pair(1, -1)  // diagonal (top-right to bottom-left)
        )

        for ((dx, dy) in directions) {
            var count = 1

            // Check one direction
            for (i in 1 until 3) {
                val row = lastCell.rowIndex + i * dx
                val col = lastCell.colIndex + i * dy

                if (row < 0 || col < 0 || row >= BOARD_DIM || col >= BOARD_DIM) {
                    break
                }

                val cell = Cell(row, col)

                if (moves[cell] == player) {
                    count++
                    if (count == 3) {
                        return true
                    }
                } else {
                    break
                }
            }

            // Check the opposite direction
            for (i in 1 until 3) {
                val row = lastCell.rowIndex - i * dx
                val col = lastCell.colIndex - i * dy

                if (row < 0 || col < 0 || row >= BOARD_DIM || col >= BOARD_DIM) {
                    break
                }

                val cell = Cell(row, col)

                if (moves[cell] == player) {
                    count++
                    if (count == 3) {
                        return true
                    }
                } else {
                    break
                }
            }
        }

        return false

    }

}



