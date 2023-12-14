package Gomoku.CreateGame


import Gomoku.DomainModel.BOARD_DIM
import Gomoku.DomainModel.Board
import Gomoku.DomainModel.Cell
import Gomoku.DomainModel.Game
import Gomoku.DomainModel.Models.WaitingRoom
import Gomoku.DomainModel.Player
import Gomoku.DomainModel.moves
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.toOpeningRule
import Gomoku.DomainModel.toVariante
import Gomoku.DomainModel.variantes

import Gomoku.Services.CreateGameService
import Gomoku.Services.FetchGameException
import Gomoku.Services.FetchUser1Exception
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

class CreateGameAct(
    val client: OkHttpClient,
    val gson: Gson
) : CreateGameService {

    override suspend fun fetchWaitingRoom(idplayer: Int?): WaitingRoom? {
        val requestBodyJson = gson.toJson("id" to idplayer)
        val requestBody =
            RequestBody.create("application/json".toMediaTypeOrNull(), requestBodyJson)
        val request = Request.Builder().url("$LINK/games/waitingRoom").post(requestBody)
            .build()
        return suspendCoroutine { continuation ->
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(FetchUser1Exception("Failed to create", e))
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null) {
                        continuation.resumeWithException(FetchGameException("Failed to try to create a game : ${response.code}"))
                    } else {
                        try {
                            val waitingRoom =
                                gson.fromJson(body.string(), WaitingRoomDto::class.java)
                                    .toWaitingRoom()
                            continuation.resume(waitingRoom)
                        } catch (e: Exception) {
                            continuation.resumeWithException(e)
                        }
                    }
                }
            })
        }
    }


    override suspend fun getGameString(id: Int): Game? {
        TODO()
    }


    override suspend fun fetchCreateGame(id: Int?, openingrule: openingrule?, variantes: variantes, authToken: String): WaitingRoom {
        val requestBodyJson = gson.toJson(mapOf("player" to id, "openingRule" to openingrule, "variante" to variantes))
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), requestBodyJson)
        val request = Request.Builder()
            .url("$LINK/games/matchMaking")
            .addHeader("accept", "application/vnd.siren+json")
            .addHeader("Authorization", "Bearer $authToken")
            .post(requestBody)
            .build()

        return suspendCoroutine { continuation ->
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(FetchUser1Exception("Failed to create", e))
                    Log.v("WAITING, ", "waiting inside fetch = ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null) {
                        Log.v("WAITING, ", "waiting inside fetch = ${response.code}")
                        continuation.resumeWithException(FetchGameException("Failed to try to create a game : ${response.code}"))
                    } else {
                        val jsonString = body.string()
                        val jsonObject = gson.fromJson(jsonString, Map::class.java)
                        val values = transformToWaitingRoom(jsonObject)
                        Log.v("GETGAME", "getGame = $values")
                        continuation.resume(values!!)
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
            val board = properties?.get("board") as? Map<*, *>
            val finalBoard = transformBoard(board)
            val variante = properties?.get("variante") as? String
            val openingRule = properties?.get("openingRule") as? String
            val playera = properties?.get("playera") as? Double
            val playerb = properties?.get("playerb") as? Double
            val winner = properties?.get("winner") as? Double
            val turn = properties?.get("turn") as? Double
            if (id != null || finalBoard != null || playera != null || playerb != null || winner != null || turn != null) {
                return Game(id?.toInt(), finalBoard, playera?.toInt(), playerb?.toInt(), variante!!.toVariante(), openingRule!!.toOpeningRule(), winner?.toInt(), turn?.toInt())
            }
        }
        return null
    }

    fun transformToWaitingRoom(jsonObject: Map<*, *>): WaitingRoom? {
        if (jsonObject.containsKey("properties")) {
            val properties = jsonObject["properties"] as? Map<*, *>
            val id = properties?.get("id") as Double
            val player1 = properties?.get("player1") as Double
            val player2 = properties?.get("player2") as? Double
            val variante = properties?.get("variante") as? String
            val openingRule = properties?.get("openingRule") as? String
            val gameID = properties?.get("gameID") as? Double
            if ( player2 != null || variante != null || openingRule != null) {
                Log.v("WAITING, ", "waiting inside transform = $id")
                return WaitingRoom(id.toInt(), player1.toInt(), player2?.toInt(), variante!!.toVariante(), openingRule!!.toOpeningRule(), gameID?.toInt())
            }
        }
        return null
    }

    fun StringToBoard(boardString: String?, variante: variantes, openingRule: openingrule): Board {
        if (boardString == null) {
            return Board(emptyMap(), null, null)
        }
        if (variante == variantes.NORMAL) BOARD_DIM = 15 else BOARD_DIM = 19
        val stringaux = boardString.replace("\n", "")
        val boardCells = mutableMapOf<Cell, Player>()
        if (variante == variantes.OMOK) {
            for (row in 0 until BOARD_DIM) {
                for (col in 0 until BOARD_DIM) {
                    val player = Player.fromChar(stringaux[row * BOARD_DIM + col])
                    boardCells[Cell(row, col)] = player
                }
            }
        } else {
            for (row in 0 until BOARD_DIM) {

                for (col in 0 until BOARD_DIM) {
                    val s = stringaux[row * (BOARD_DIM) + col]

                    if (stringaux[row * (BOARD_DIM) + col] != ' ') {
                        val player = Player.fromChar(stringaux[row * BOARD_DIM + col])
                        boardCells[Cell(row, col)] = player
                    }


                }
            }
        }
        return Board(boardCells, turn = Player.PLAYER_X)
    }

    private data class WaitingRoomDto(
        val id: Int,
        val player1: Int,
        val player2: Int,
        val variante: variantes,
        val openingRule: openingrule,
        val gameID: Int?,
        val url: String
    ) {
        fun toWaitingRoom(): WaitingRoom {
            return WaitingRoom(
                id = id,
                playera = player1,
                playerb = player2,
                variante = variante,
                openingRule = openingRule,
                gameID = gameID,

                )
        }
    }


    fun transformBoard(mapa: Map<*, *>?): Board {

        val board = mapa?.get("moves") as moves
        val boardCells = mutableMapOf<Cell, Player>()

        return Board(board, turn = Player.PLAYER_X)
    }
}


