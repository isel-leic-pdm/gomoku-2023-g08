package Gomoku.model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pt.isel.tds.gomoku.model.BoardRun
import pt.isel.tds.gomoku.model.BoardWin
import pt.isel.tds.gomoku.model.Cell
import pt.isel.tds.gomoku.model.Player
import pt.isel.tds.gomoku.model.play
import pt.isel.tds.gomoku.model.totalMoves





class ViewModel() {
    var game by mutableStateOf<Game?>(CreateGame(Player.RED))
        private set
    val board get() = checkNotNull(game).board
    // Creates a new game with the given name and close the dialog
    fun newGame( player: Player) =
        tryRun {
            val g = game
            if (g == null) {
                game = CreateGame(player)
            }
        }

    private inline fun tryRun(block: () -> Unit) =
        try {
            block()
        } catch (e: IllegalStateException) {

        }
    // Plays the given position in the game
    fun play(cell: Cell) =
        try {
            if(game?.isWinner != true) {
                game = game?.play(cell)
            }
            else {
                println("Game is over")
            }

        } catch (e: Exception) {
                println(e)
            }

    fun Winner(player: Player) = tryRun {
        game = game?.Winner(player)
    }
    val Game.isWinner get() = game?.board is BoardWin
}
/*

    //Pass the game
    fun pass() = tryRun {
        gamepass = true
        game = game?.pass(storage)
        loopAutoRefresh()
    }


    // Joins the game with the given name and close the dialog
    fun joinGame(name: String) =
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
    fun refresh() {
        scope.launch {
            tryRunS {
                game = game?.refresh(storage)
            }
        }
    }

    //Activates or deactivates the targets
    fun targets() {
        if (game == null) return
        val str = if (targetsFlag) "ON" else "OFF"
        game = game?.targets(str, storage)
        targetsFlag = !targetsFlag
    }

    val canRefreshAuto get() = mayRefresh() && autoRefresh && game is MultiGame
    val canRefresh get() = mayRefresh() && !autoRefresh && game is MultiGame
    fun mayRefresh(): Boolean {
        val g = game ?: return false
        return g.board !is BoardWin && g.board !is BoardDraw || g.board !is BoardPass
    }

    //Check if a given piece is to be rotated
    fun cellsRotating(pos: Cell): Boolean {
        return cellsChanged.contains(pos)
    }

    //Checks if cell is a playable cell
    fun cellsTarget(cell: Cell): Boolean {
        if (!targetsFlag) return false
        return game?.board?.playableCells()?.contains(cell) == true
    }


    fun isGamePass(): Boolean {
        return game?.board?.isPass ?: false
    }

    fun isNewGame(): Boolean {
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

    //endregion

    //region Refresh State
    var autoRefresh by mutableStateOf(false)
        private set

    /**
     *
     */
    fun toggleAutoRefresh() {
        autoRefresh = !autoRefresh
        if (autoRefresh) loopAutoRefresh()
        else refresh?.let {
            it.cancel()
            refresh = null
        }
    }

    private var refresh: Job? = null
    private fun loopAutoRefresh() {
        if (autoRefresh && mayRefresh()) {
            refresh = scope.launch {
                while (true) {
                    game = game?.refresh(storage, checked = false)
                    if (!mayRefresh()) break
                    delay(2000)
                }
            }
        }
    }

    //endregion
    fun closeMessageDialog() {
        closeDialog()
        message = ""
        openDialog(Dialog.MESSAGE) // Reopen the underlying dialog
    }

    //region Dialogs State
    // The dialog opened
    var open by mutableStateOf<Dialog?>(null)
        private set

    fun openDialog(d: Dialog) {
        open = d
    }

    private fun openMessageDialog(e: Exception) {
        message = e.message ?: "Unknown error"
        openDialog(Dialog.MESSAGE)
    }

    fun closeDialog() {
        open = null
    }
    //endregion

    //region Status information
    // The status of the game to be displayed in status bar
    val status: StatusInfo
        get() = when (val b = game?.board) {
            is BoardRun -> "Turn" of b.turn
            is BoardWin -> "Winner" of b.winner
            is BoardDraw -> "Draw" of null
            null -> "No Game being Played!" of null
        }
    //endregion

    //region Auxiliary Functions
    private fun CoroutineScope.tryRunS(block: suspend () -> Unit) =
        launch {
            try {
                block()
            } catch (e: IllegalStateException) {
                openMessageDialog(e)
            }
        }

    private inline fun tryRun(block: () -> Unit) =
        try {
            block()
        } catch (e: IllegalStateException) {
            openMessageDialog(e)
        }
    //endregion

 */



// Dialogs of the application
enum class Dialog { NEW, JOIN, MESSAGE }

// Status of the game to be displayed in status bar
data class StatusInfo(val label: String, val player: Player?)

infix fun String.of(player: Player?) = StatusInfo(this, player)
