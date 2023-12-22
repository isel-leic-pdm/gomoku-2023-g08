package Gomoku.PlayGame

import Gomoku.DomainModel.BOARD_DIM
import Gomoku.DomainModel.Board
import Gomoku.DomainModel.Cell
import Gomoku.DomainModel.Game
import Gomoku.DomainModel.Player
import Gomoku.DomainModel.toOpeningRule
import Gomoku.DomainModel.toVariante
import Gomoku.DomainModel.variantes
import Gomoku.Services.FetchGameException

import Gomoku.Services.FetchUser1Exception
import Gomoku.Services.PlayGameService
import Gomoku.app.LINK
import android.util.Log

import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.IOException

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PlayGameAct(
    val client: OkHttpClient,
    val gson: Gson
) : PlayGameService {

    override suspend fun play(
        id: Int?,
        line: Int,
        col: Int,
        authToken: String?,
        idGame: Int
    ): Game? {
        val lineplay = line + 1
        val colplay = (col + 65).toChar()
        val json = gson.toJson(mapOf("userId" to id, "l" to lineplay, "c" to colplay))
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)


        val request by lazy {
            Request.Builder()
                .url("$LINK/games/play/$idGame")
                .addHeader("Authorization", "Bearer $authToken")
                .addHeader("accept", "application/vnd.siren+json")
                .post(requestBody)
                .build()
        }

        return suspendCoroutine {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.v("PLAY", "onfailure")
                    it.resumeWithException(FetchUser1Exception("Failed to fetch User", e))
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null) {
                        Log.v("PLAY", "onresponse-nao deu certo ")
                        it.resumeWithException(FetchUser1Exception("Failed to fetch User: ${response.code}"))
                    } else {
                        val json = body.string()
                        val jsonObject = gson.fromJson(json, Map::class.java)
                        val values = transformGame(jsonObject)
                        it.resume(values)
                        Log.v("PLAY", "onresponse")
                    }
                }
            })
        }
    }

    override suspend fun getGame(gameID: Int?): Game? {

        val request = Request.Builder().url("$LINK/games/get/$gameID")
            .addHeader("accept", "application/vnd.siren+json")
            .get()
            .build()
        return suspendCoroutine { continuation ->
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(FetchUser1Exception("Failed to create", e))
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null) {
                        Log.v("GETGAME", "getGame erro  = ${response.code}")
                        continuation.resumeWithException(FetchGameException("Failed to try to create a game : ${response.code}"))
                    } else {
                        val jsonString = body.string()
                        val jsonObject = gson.fromJson(jsonString, Map::class.java)
                        val values = transformGame(jsonObject)
                        Log.v("GETGAME", "getGame = $values")
                        continuation.resume(values)

                    }
                }
            })
        }
    }

    fun transformGame(jsonObject: Map<*, *>): Game? {
        if (jsonObject.containsKey("properties")) {
            val properties = jsonObject["properties"] as? Map<*, *>
            val id = properties?.get("id") as? Double
            val board = properties?.get("board") as String
            val variante = properties?.get("variante") as? String
            val finalBoard = transformBoard(board, variante!!.toVariante())

            val openingRule = properties?.get("openingRule") as? String
            val playera = properties?.get("player1") as? Double
            val playerb = properties?.get("player2") as? Double
            val winner = properties?.get("winner") as? Double
            val turn = properties?.get("turn") as? Double
            if (id != null || finalBoard != null || playera != null || playerb != null || winner != null || turn != null) {

                return Game(
                    id?.toInt(),
                    finalBoard,
                    playera?.toInt(),
                    playerb?.toInt(),
                    variante!!.toVariante(),
                    openingRule!!.toOpeningRule(),
                    winner?.toInt(),
                    turn?.toInt()
                )
            }
        }
        return null
    }


    fun transformBoard(s: String, variante: variantes): Board {
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
        } // esta correto
        else {
            for (row in 0 until BOARD_DIM ) {
                for (col in 0 until BOARD_DIM ) {
                    val s = stringaux[row * (BOARD_DIM) + col]

                    //     if(stringaux[row * (BOARD_DIM) + col] != '') {
                    val player = Player.fromChar(stringaux[row * BOARD_DIM + col])
                    boardCells[Cell(row, col)] = player
                    //   }


                }
            }
        }

     Log.v("PLAYGAME", "boardCells = $boardCells")
        return Board(boardCells, turn = Player.PLAYER_X, null)
    }
}



