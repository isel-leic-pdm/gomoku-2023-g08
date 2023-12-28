package Gomoku.DataStore.Domain



import Gomoku.DataStore.Domain.UserInfo
import Gomoku.DataStore.Domain.UserInfoRepository
import Gomoku.DomainModel.BOARD_DIM
import Gomoku.DomainModel.Board
import Gomoku.DomainModel.Cell
import Gomoku.DomainModel.Game
import Gomoku.DomainModel.Models.WaitingRoom
import Gomoku.DomainModel.Player
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.toColumn
import Gomoku.DomainModel.toOpeningRule
import Gomoku.DomainModel.toRow
import Gomoku.DomainModel.toVariante
import Gomoku.DomainModel.variantes

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first


private const val GAME_ID = "id_game"
private const val BOARD = "board"
private const val PLAYER1 = "player1"
private const val PLAYER2 = "player2"
private const val VARIANTE = "variante"
private const val OPENINGRULE = "openingrule"
private const val WINNER = "winner"
private const val TURN = "turn"
private const val URL = "url"


class GameInfoDataStorage(private val store: DataStore<Preferences>) : GameInfoRepository {
    private val id_game = stringPreferencesKey(GAME_ID)
    private val board = stringPreferencesKey(BOARD)
    private val player1 = stringPreferencesKey(PLAYER1)
    private val player2 = stringPreferencesKey(PLAYER2)
    private val variante = stringPreferencesKey(VARIANTE)
    private val openingrule = stringPreferencesKey(OPENINGRULE)
    private val winner = stringPreferencesKey(WINNER)
    private val turn = stringPreferencesKey(TURN)
    private val url = stringPreferencesKey(URL)



    override suspend fun getCurrentGame(): Game {
        val preferences = store.data.first()
        val storedVariante = preferences[variante]
        val storedOpeningrule = preferences[openingrule]
        val storedId = preferences[id_game]?.toInt()
        val storedBoard = preferences[board]?.fromString(storedVariante?.toVariante()!!, storedOpeningrule!!.toOpeningRule())
        val storedPlayer1 = preferences[player1]?.toInt()
        val storedPlayer2 = preferences[player2]?.toInt()

        val storedWinner = preferences[winner]?.toInt()
        val storedTurn = preferences[turn]?.toInt()
        val storedUrl = preferences[url]

        Log.v("123456", "get = $storedId, $storedBoard, $storedPlayer1, $storedPlayer2, $storedVariante, $storedOpeningrule, $storedWinner, $storedTurn, $storedUrl")

        return Game(storedId!!, storedBoard!!, storedPlayer1, storedPlayer2, storedVariante?.toVariante(), storedOpeningrule!!.toOpeningRule(), storedWinner, storedTurn)
    }

    override suspend fun rightCurrentGame(game: Game){
        store.edit { preferences ->
            preferences[id_game] = game.id.toString()
            preferences[board] = game.board.toString()
            preferences[player1] = game.player1.toString()
            preferences[player2] = game.player2.toString()
            preferences[variante] = game.variante.toString()
            preferences[openingrule] = game.openingrule.toString()
            preferences[winner] = game.winner.toString()
            preferences[turn] = game.turn.toString()

        }

    }

}

fun String.fromString(variante: variantes, openingrule: openingrule): Board {
    if(variante == variantes.NORMAL) BOARD_DIM = 15 else BOARD_DIM = 19
    val stringaux = this.replace("\n", "")
    val boardCells = mutableMapOf<Cell, Player>()
    if(variante == variantes.OMOK) {
        for (row in 0 until BOARD_DIM) {
            for (col in 0 until BOARD_DIM) {
                val player = Player.fromChar(stringaux[row * BOARD_DIM + col])
                boardCells[Cell(row.toRow(), col.toColumn())] = player
            }
        }
    } // esta correto
    else {
        for (row in 0 until BOARD_DIM ) {
            for (col in 0 until BOARD_DIM ) {
                val s = stringaux[row * (BOARD_DIM) + col]

                //     if(stringaux[row * (BOARD_DIM) + col] != '') {
                val player = Player.fromChar(stringaux[row * BOARD_DIM + col])
                boardCells[Cell(row.toRow(), col.toColumn())] = player
                //   }


            }
        }
    }


    return Board(boardCells, turn = Player.PLAYER_X, null)
}





