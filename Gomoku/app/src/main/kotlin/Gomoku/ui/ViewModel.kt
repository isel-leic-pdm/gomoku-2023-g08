package Gomoku.ui
/*
package pt.isel.tds.reversi.ui

import BoardSerializer
import TextFileStorage
import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pt.isel.tds.reversi.model.*


/**
 * View model of the application.
 * Manages the state of the game and open dialogs.
 */

class ViewModel(val scope:CoroutineScope) {

    var message = ""
        private set
    // Game being played
   // var game by mutableStateOf<Game?>(null)

    val board get() =

    // Creates a new game with the given name and close the dialog
    fun newGame(name: String, player: Player, multigame: Boolean) =
        tryRun {
            val g = game
            if (g == null || !g.board.isNew) {
                if (multigame) {
                    isMultiGame = true
                    game = createMultiGame(name, player, storage)
                } else {
                    game = createSingleGame(player)
                    isMultiGame = false
                }
                targetsFlag = false
                closeDialog()
            }
        }


    // Plays the given position in the game
    fun play(cell: Cell) =
        try {
            val previousBoard = game?.board as BoardRun
            game = game?.play(cell, storage)
            cellsChanged = (previousBoard.moves.keys + (game?.board as BoardRun).moves.keys).filter {
                previousBoard.moves[it] != (game?.board as BoardRun).moves[it] && previousBoard.moves[it] != null
            }.toSet()
            loopAutoRefresh()
        }
        catch (e: Exception) {
            println(e)
        }

    //Pass the game
    fun pass() = tryRun {
        gamepass = true
        game = game?.pass(storage)
        loopAutoRefresh()
    }


    // Joins the game with the given name and close the dialog
    fun joinGame(name:String) =
        scope.launch {
            tryRunS {
                joined = true
                game = joinGame(name, storage)
                isMultiGame = true
                closeDialog()
                loopAutoRefresh()
            }
        }

    //Refresh the game when called
    fun refresh(){
        scope.launch{
            tryRunS {
                game = game?.refresh(storage)
            }
        }
    }

    //Activates or deactivates the targets
    fun targets(){
        if (game == null) return
        game = game?.targets(targetsFlag,storage)
        targetsFlag = !targetsFlag
    }


    val canRefresh get() = mayRefresh() &&  !autoRefresh
    fun  mayRefresh():Boolean{
        val g = game?: return false
        return g.board !is BoardWin && g.board !is BoardDraw || g.board !is BoardPass
    }
    //Check if a given piece is to be rotated
    fun cellsRotating(pos: Cell): Boolean {
        return cellsChanged.contains(pos)
    }

    //Checks if cell is a playable cell
    fun cellsTarget(cell: Cell) :Boolean{
        if(!targetsFlag) return false
        return game?.board?.playableCells()?.contains(cell) == true
    }


    fun isGamePass():Boolean {
        return game?.board?.isPass ?: false
    }

    fun isNewGame():Boolean{
        return game?.board?.isNew ?: false
    }

    fun getPoints(player: Player): Int {
        return board.moves.count { it.value == player }

    }

    fun isMyTurn(): Boolean {
        return when (val b = game?.board) {
            is BoardRun -> {
                if (game is MultiGame) {
                    b.turn == (game as MultiGame).player
                } else {
                    false
                }
            }
            else -> false
        }
    }


    // The dialog opened
    var open by mutableStateOf<Dialog?>(null)
        private set
    fun openDialog(d:Dialog) { open = d }
    fun closeDialog() { open = null }

    // The status of the game to be displayed in status bar
    val status:StatusInfo
        get() = when (val b = game?.board) {
            is BoardRun -> "Turn" of b.turn
            is BoardWin -> "Winner" of b.winner
            is BoardDraw -> "Draw" of null
            null -> "No Game being Played!" of null
        }

    private suspend fun tryRunS(f:suspend ()->Unit){
        try {
            f()
        }
        catch (e: Exception){
            message = e.message ?: "Unknown error"
            openDialog(Dialog.MESSAGE)
        }
    }
    private  fun tryRun(f: ()->Unit){
        try {
            f()
        }
        catch (e: Exception){
            message = e.message ?: "Unknown error"
            openDialog(Dialog.MESSAGE)
        }
    }
    fun closeMessageDialog() {
        closeDialog()
        message = ""
        openDialog(Dialog.MESSAGE) // Reopen the underlying dialog
    }
}

// Dialogs of the application
enum class Dialog { NEW, JOIN,MESSAGE }
// Status of the game to be displayed in status bar
data class StatusInfo(val label: String, val player: Player?)
infix fun String.of(player: Player?) = StatusInfo(this, player)

 */
