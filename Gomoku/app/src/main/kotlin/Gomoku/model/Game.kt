package Gomoku.model

import android.util.Log
import pt.isel.tds.gomoku.model.Board
import pt.isel.tds.gomoku.model.BoardRun
import pt.isel.tds.gomoku.model.Cell

import pt.isel.tds.gomoku.model.Player

import pt.isel.tds.gomoku.model.play
import pt.isel.tds.gomoku.model.totalMoves




//typealias BoardStorage = Storage<String, Board>

/**
 * Represents the game.
 * Store the board and the target mode.
 * @property board the board of the game.
 * @property target target mode of the game.
 */
sealed class Game(val board: Board)
class SingleGame(
    board: Board,

) : Game(board)
/*
class MultiGame(
    board: Board,
    val player: Player,
    val id: String,
    target: Boolean,
) : Game(board,target)

 */



fun CreateGame(player: Player): Game =
    SingleGame(board = createBoard(player))

fun createBoard(player: Player): Board {
    val board = BoardRun(totalMoves, player)
    return board
}

fun Game.play(cell: Cell, /*st: BoardStorage */): Game {
    Log.v("Game", "Game.play called with cell: $cell")
   // val newBoard = board.play(cell)
    return SingleGame( board.play(cell))
}
fun Game.Winner(player: Player): Game {
    Log.v("Game", "Game.Winner called with player: $player")
    // val newBoard = board.play(cell)
    return SingleGame(board)
}





