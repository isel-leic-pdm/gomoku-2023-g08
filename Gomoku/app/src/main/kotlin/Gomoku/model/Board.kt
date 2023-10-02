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

    // Check horizontal
    var count = 1
    for (col in (lastCell.colIndex + 1) until BOARD_DIM) {
        val cell = Cell(lastCell.rowIndex, col)
        if (moves[cell] == player) {
            count++
            if (count == 5) return true
        } else {
            break
        }
    }
    for (col in (lastCell.colIndex - 1) downTo 0) {
        val cell = Cell(lastCell.rowIndex, col)
        if (moves[cell] == player) {
            count++
            if (count == 5) return true
        } else {
            break
        }
    }

    // Check vertical -certo
    count = 1
    for (row in (lastCell.rowIndex + 1) until BOARD_DIM) {
        val cell = Cell(row, lastCell.colIndex)
        if (moves[cell] == player) {
            count++
            if (count == 5) return true
        } else {
            break
        }
    }
    for (row in (lastCell.rowIndex - 1) downTo 0) {
        val cell = Cell(row, lastCell.colIndex)
        if (moves[cell] == player) {
            count++
            if (count == 5) return true
        } else {
            break
        }
    }

    // Check diagonal (top-left to bottom-right)
    count = 1
    for (i in 1 until BOARD_DIM) {
        val row = lastCell.rowIndex - i
        val col = lastCell.colIndex - i
        if (row < 0 || col < 0) break
        val cell = Cell(row, col)
        if (moves[cell] == player) {
            count++
            if (count == 5) return true
        } else {
            break
        }
    }
    for (i in 1 until BOARD_DIM) {
        val row = lastCell.rowIndex + i
        val col = lastCell.colIndex + i
        if (row >= BOARD_DIM || col >= BOARD_DIM) break
        val cell = Cell(row, col)
        if (moves[cell] == player) {
            count++
            if (count == 5) return true
        } else {
            break
        }
    }

    // Check diagonal (top-right to bottom-left)
    count = 1
    for (i in 1 until BOARD_DIM) {
        val row = lastCell.rowIndex - i
        val col = lastCell.colIndex + i
        if (row < 0 || col >= BOARD_DIM) break
        val cell = Cell(row, col)
        if (moves[cell] == player) {
            count++
            if (count == 5) return true
        } else {
            break
        }
    }
    for (i in 1 until BOARD_DIM) {
        val row = lastCell.rowIndex + i
        val col = lastCell.colIndex - i
        if (row >= BOARD_DIM || col < 0) break
        val cell = Cell(row, col)
        if (moves[cell] == player) {
            count++
            if (count == 5) return true
        } else {
            break
        }
    }

    return false
}

private fun BoardRun.winner(moves: Moves) =
   if(moves.values.count { it == turn } == 5){
       turn
   }
    else turn.other()














