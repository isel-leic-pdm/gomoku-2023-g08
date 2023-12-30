package Gomoku.ReplayGames



import Gomoku.DomainModel.BOARD_DIM
import Gomoku.DomainModel.Board
import Gomoku.DomainModel.Cell
import Gomoku.DomainModel.GameReplayShowModel

import Gomoku.DomainModel.Player
import Gomoku.DomainModel.toOpeningRule
import Gomoku.DomainModel.toVariante
import Gomoku.DomainModel.variantes
import Gomoku.Services.FetchGameException
import Gomoku.Services.ReplayGameService
import Gomoku.app.LINK
import android.annotation.SuppressLint
import android.util.Log


import com.google.gson.Gson
import okhttp3.Call

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ReplayGameAct(
    val client: OkHttpClient,
    val gson: Gson
) : ReplayGameService {


    override suspend fun fetchReplayGame(id: Int): List<GameReplayShowModel> {
        val request by lazy {
            Request.Builder()
                .url("$LINK/games/ReWatch/$id")
                .addHeader("accept", "application/vnd.siren+json")
                .build()
        }
        return suspendCoroutine { continuation ->
            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    Log.v("EIU", "error : " + e.toString())
                    continuation.resumeWithException(FetchGameException("Failed to create", e))
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null) {
                        Log.v("GETGAME", "getGame erro  = ${response.code}")
                        continuation.resumeWithException(FetchGameException("Failed to try to create a game : ${response.code}"))
                    } else {
                        val jsonString = body.string()
                        val jsonObject = gson.fromJson(jsonString, Map::class.java)
                        val values = transformListGameString(jsonObject)
                        Log.v("GETGAME", "getGame = $values")
                        continuation.resume(values)

                    }

                }
            })
        }
    }
    fun transformListGameString(jsonObject: Map<*, *>): List<GameReplayShowModel>{
        if (jsonObject.containsKey("properties")) {
            val properties = jsonObject["properties"] as List<Map<*, *>>
            val listToReturn = mutableListOf<GameReplayShowModel>()
            for (i in properties.indices) {
                val id = properties[i]["game_id"] as? Double
                val player = properties[i]["player"] as? Double
                val turn = properties[i]["turn"] as? Double
                val variant = properties[i]["variant"] as String
                val openingRule = properties[i]["openingRule"] as? String
                val line = properties[i]["line"] as? Double
                val col = properties[i]["col"] as? String
                val board = properties[i]["board"] as? String
                val finalBoard = transformBoardString(board, variant.toVariante())
                val game = GameReplayShowModel(
                    id?.toInt()!!,
                    player?.toInt()!!,
                    turn?.toInt()!!,
                    variant.toVariante(),
                    openingRule!!.toOpeningRule(),
                    line?.toInt()!!,
                    col?.get(0) ?: 'A',
                    finalBoard
                )
                listToReturn.add(game)
            }
            Log.v("LISTS", "listToReturn = ${listToReturn.size}")
            return listToReturn

        }
        return emptyList()
    }
    @SuppressLint("SuspiciousIndentation")
    fun transformBoardString(string: String?, variante: variantes): Board {
        if(variante == variantes.NORMAL)
            BOARD_DIM = 15 else BOARD_DIM = 19
        val stringaux = string?.replace("\n", "")
        val boardCells = mutableMapOf<Cell, Player>()
        if(variante == variantes.OMOK) {
            for (row in 0 until BOARD_DIM) {
                for (col in 0 until BOARD_DIM) {
                    val player = Player.fromChar(stringaux?.get(row * BOARD_DIM + col) ?: Player.EMPTY.toChar())
                    boardCells[Cell(row, col)] = player
                }
            }
        } // esta correto
        else {
            for (row in 0 until BOARD_DIM ) {
                for (col in 0 until BOARD_DIM ) {
                    val player = Player.fromChar(stringaux?.get(row * BOARD_DIM + col) ?: Player.EMPTY.toChar() )
                    Log.v("GAMESTRING", "board after poll = $stringaux")
                    Log.v("PLAYGAME", "player = $player")
                    boardCells[Cell(row, col)] = player


                }
            }
        }

        Log.v("PLAYGAME", "boardCells = $boardCells")
        return Board(boardCells, turn = Player.PLAYER_X, null)
    }
}



