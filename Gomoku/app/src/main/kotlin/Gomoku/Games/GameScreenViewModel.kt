package Gomoku.Games
import Gomoku.Services.GamesService
import Gomoku.State.Idle
import Gomoku.State.LoadState
import Gomoku.State.Loaded
import Gomoku.State.Loading

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class GameScreenViewModel() : ViewModel() {
    var game by mutableStateOf<LoadState>(Idle)
        private set


    fun fetchGame(servicegame: GamesService) {
        viewModelScope.launch {
            game = Loading
            game = Loaded(
                runCatching {
                    servicegame.fetchGame()
                }
            )
        }
    }
        /*
    fun play(cell: Cell){
        viewModelScope.launch {
            game = Loading
            game = Loaded(runCatching { game.play(cell) })
        }


 */


    }




/*
    private inline fun tryRun(block: () -> Unit) =
        try {
            block()
        } catch (e: IllegalStateException) {

        }
    // Plays the given position in the game

    fun play(cell: Cell) =
        try {
            if(game is Loaded) {

                Log.v("GameScreenViewModel", "GameScreenViewModel.play called with cell: $cell")
                val newGame = g.value.play(cell)
                game = Loaded(newGame)

            }  else {
                println("Game is over")
            }

        } catch (e: Exception) {
            println(e)
        }

     */


/*

    fun CreateGame(player: Player): Game =

    fun createBoard(player: Player): Board {
        val board = BoardRun(totalMoves, player)
        return board
    }

    fun Game.play(cell: Cell): Game {
        Log.v("Game", "Game.play called with cell: $cell")
        // val newBoard = board.play(cell)
        return SingleGame( board.play(cell))
    }
    fun Game.Winner(player: Player): Game {
        Log.v("Game", "Game.Winner called with player: $player")
        // val newBoard = board.play(cell)
        return SingleGame(board)
    }

 */

