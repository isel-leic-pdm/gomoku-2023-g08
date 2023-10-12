package pt.isel.tds.gomoku.model

import Gomoku.model.Game
import android.util.Log


const val BOARD_DIM =15
const val MAX_MOVES = BOARD_DIM * BOARD_DIM

enum class Player { RED, YELLOW;
    fun other() = if (this==RED) YELLOW else RED
}

// Type to represents all the moves in the game.
typealias Moves = Map<Cell, Player>
val totalMoves = emptyMap<Cell, Player>()


sealed class Board(val moves: Moves,val  turn : Player) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Board) return false
        if (this::class != other::class) return false
        return moves.size == other.moves.size
    }
    override fun hashCode(): Int = moves.hashCode()
}
open class BoardRun(moves: Moves, turn: Player): Board(moves, turn)
class BoardWin(moves: Moves, val winner: Player): Board(moves, winner)
class BoardDraw(moves: Moves) : Board(moves, Player.RED)

fun Board.play(cell: Cell): Board = when(this) {
    is BoardRun -> {
        check(moves[cell] == null) { "Position $cell is not empty" }
        val newMoves = moves + (cell to turn)
        when {
            isWin(moves) -> BoardWin(moves, winner(moves))
            moves.size == MAX_MOVES-> BoardDraw(moves)
            else -> {
                BoardRun(newMoves, turn = turn.other())
            }
        }
    }
    else ->  error("Game is over")
}


private fun BoardRun.isWin(moves: Moves): Boolean {
    val lastMove = moves.entries.lastOrNull() ?: return false
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



private fun BoardRun.winner(moves: Moves) =
   if(moves.values.count { it == turn } == 5){
       turn
   }
    else turn.other()














