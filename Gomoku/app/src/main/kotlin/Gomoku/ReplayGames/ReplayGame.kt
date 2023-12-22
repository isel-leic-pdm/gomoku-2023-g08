package Gomoku.ReplayGames



import Gomoku.DomainModel.BOARD_DIM
import Gomoku.DomainModel.Cell
import Gomoku.DomainModel.Column

import Gomoku.DomainModel.Player
import Gomoku.DomainModel.moves
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.toColumn
import Gomoku.DomainModel.toRow
import Gomoku.DomainModel.variantes
import Gomoku.Services.FetchGameException
import Gomoku.Services.ReplayGameInterface
import Gomoku.State.ReplayGameModel
import Gomoku.app.LINK
import Row
import android.util.Log
import androidx.core.graphics.toColor


import com.google.gson.Gson

import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ReplayGameAct(
    val client: OkHttpClient,
    val gson: Gson
) : ReplayGameInterface {


    override suspend fun fetchReplayGame(id: Int): List<ReplayGameModel> {
        val request by lazy {
            Request.Builder()
                .url("$LINK/games/replay/$id")
                .addHeader("accept", "application/vnd.siren+json")
                .build()
        }
        Log.v("EIU", "request : " + request.toString())
        Log.v("EIU", id.toString())

        return suspendCoroutine { continuation ->
            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    Log.v("EIU", "error : " + e.toString())
                    continuation.resumeWithException(FetchGameException("Failed to create", e))
                }


                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    val body = response.body
                    Log.v("EIU", "body : " + body.toString())
                    if (!response.isSuccessful || body == null) {
                        continuation.resumeWithException(FetchGameException("Failed to create user: ${response.code}"))
                    } else {
                        val jsonString = body.string()
                        Log.v("EIU", "jsonString : " + jsonString.toString())
                        val jsonObject = gson.fromJson(jsonString, Map::class.java)
                        Log.v("EIU", "jsonObject : " + jsonObject.toString())
                        val values = transform(jsonObject)
                        Log.v("EIUU", "values : " + values.toString())

                        continuation.resume(values)
                    }
                }
            })
        }
    }

    /*
    override suspend fun fetchRanking(): List<UsersRankOutput> {
        return suspendCoroutine { continuation ->
            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    continuation.resumeWithException(FetchGameException("Failed to create", e))
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null) {
                        continuation.resumeWithException(FetchGameException("Failed to create user: ${response.code}"))
                    } else {
                        val jsonString = body.string()
                        val jsonObject = gson.fromJson(jsonString, Map::class.java)
                        val values = transform(jsonObject)
                        Log.v("RANKING","values : "+ values.toString())

                        continuation.resume(values)
                    }
                }
            })
        }
    }

}

     */

    fun transform(jsonObject: Map<*, *>): List<ReplayGameModel> {
        val listToReturn = mutableListOf<ReplayGameModel>()

        if (jsonObject.containsKey("properties")) {
            val properties = jsonObject["properties"] as? List<Map<*, *>>
            Log.v("EIU", "properties : $properties")

            properties?.forEach { property ->
                Log.v("EIU", "property : $property")
                val game_id = property["game_id"]
                val player = property["player"]
                val variantes = property["variantes"]
                val openingrule = property["openingrule"]
                val turn = property["turn"]
                val line = property["line"]
                val col = property["col"]
                val board = property["board"]
                Log.v("EIU-board", "board : $board")


                if (game_id != null && player != null && turn != null && line != null && col != null) {
                    val replayGameModel = ReplayGameModel(
                        (game_id as Double).toInt(),
                        (player as Double).toInt(),
                        variantes as variantes, // Substitua 'Variantes' pelo tipo real
                        openingrule as openingrule, // Substitua 'OpeningRule' pelo tipo real
                        (turn as Double).toInt(),
                        (line as Double).toInt(),
                        col.toString(),
                        transformBoard(board, variantes)
                    )
                    Log.v("EIU-BoardToString", "ReplayGameModel : $replayGameModel")
                    listToReturn.add(replayGameModel)
                }
            }
        }

        return listToReturn
    }

    fun transformBoard(board: Any?, variante: variantes?): String {
        val boardMoves = board as Map<Cell, Player>
        Log.v("EIU-BoardToString", "boardMoves : $boardMoves")
        val bts = BoardToString(variante, boardMoves)
        Log.v("EIU-BoardToString", "bts : $bts")
        return BoardToString(variante, boardMoves)
    }


    fun BoardToString(variante: variantes? = null, moves: Map<Cell,Player>): String = buildString {
        if (variante == variantes.NORMAL) BOARD_DIM = 15 else BOARD_DIM = 19
        for (row in 0 until BOARD_DIM) {
            for (col in 0 until BOARD_DIM) {
                val cell = Cell(row.toRow(), col.toColumn())
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



/*
fun Board.BoardToString(variante: variantes? = null): String = buildString {
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

 */


