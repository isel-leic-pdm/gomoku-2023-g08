package Gomoku.CreateGame


import Gomoku.DomainModel.Board
import Gomoku.DomainModel.Cell
import Gomoku.DomainModel.Game
import Gomoku.DomainModel.Models.WaitingRoom
import Gomoku.DomainModel.Player
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.toOpeningRule
import Gomoku.DomainModel.toVariante
import Gomoku.DomainModel.variantes

import Gomoku.Services.CreateGameService
import Gomoku.Services.FetchGameException
import Gomoku.Services.FetchUser1Exception
import Gomoku.app.LINK

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
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), requestBodyJson)
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
                            val waitingRoom = gson.fromJson(body.string(), WaitingRoomDto::class.java).toWaitingRoom()
                            continuation.resume(waitingRoom)
                        } catch (e: Exception) {
                            continuation.resumeWithException(e)
                        }
                    }
                }
            })
        }
    }

    override suspend fun searchGame(id: Int?): Game? {
        TODO("Not yet implemented")
    }


    override suspend fun fetchCreateGame(id: Int?, openingrule: openingrule?, variantes: variantes, authToken: String): WaitingRoom{
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
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null) {
                        continuation.resumeWithException(FetchGameException("Failed to try to create a game : ${response.code}"))
                    } else {
                        try {
                            val waitingRoom = gson.fromJson(body.string(), WaitingRoomDto::class.java).toWaitingRoom()
                            continuation.resume(waitingRoom)
                        } catch (e: Exception) {
                            continuation.resumeWithException(e)
                        }
                    }
                }
            })
        }
    }

    override suspend fun getGame(playerA: Int, playerB: Int): Game? {
        val requestBodyJson = gson.toJson(mapOf("playera" to playerA, "playerb" to playerB))
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), requestBodyJson)
        val request = Request.Builder()
            .url("$LINK/games/getGame")
            .addHeader("accept", "application/vnd.siren+json")
            .post(requestBody)
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
                        val jsonString = body.string()
                        val jsonObject = gson.fromJson(jsonString, Map::class.java)
                        val values = transformGame(jsonObject)
                    }
                }
            })
        }
    }
    fun transformGame(jsonObject: Map<*, *>): Game? {
        if (jsonObject.containsKey("properties")) {
            val properties = jsonObject["properties"] as? Map<*, *>
            val boardProp = properties?.get("board") as? Map<*, *>
            val moves = boardProp?.get("moves") as? Map<*, *>
            val turn = boardProp?.get("turn") as? String
            val winner = boardProp?.get("winner") as? Double
             val lastMove = boardProp?.get("lastMove") as? Map<*, *>
            properties?.forEach { property ->
                val id = properties?.get("id") as? Double ?: 0.0
                val board = transformBoard(moves)
                val player1 = properties?.get("playera") as? Double
                val player2 = properties?.get("playerb") as? Double
                val turn = properties?.get("turn") as? Double
                val winner = properties?.get("winner") as? Double
                val openingRule = properties?.get("openingRule") as? String
                val variante = properties?.get("variante") as? String
                if(id != null && board != null && player1 != null && player2 != null && turn != null && winner != null && openingRule != null && variante != null) {
                    return Game(
                        id.toInt(),
                        board,
                        player1.toInt(),
                        player2.toInt(),
                        variante?.toVariante(),
                        openingRule?.toOpeningRule()!!,
                        winner?.toInt(),
                        turn?.toInt()
                    )
                } }
            }
return null
        }

    }
 fun  transformBoard(moves: Map<*, *>? ): Board {
     val Boardmoves = mutableMapOf<Cell, Player>()
        moves?.forEach { move ->
            val cell = move.key as? Cell
            val player = move.value as? Player
            if (cell != null && player != null) {
                Boardmoves[cell] = player
            }
        }
     return Board(Boardmoves,null, null)


    }

    private data class WaitingRoomDto(
        val id: Int,
        val player1: Int,
        val player2: Int,
        val variante: variantes,
        val openingRule: openingrule,
        val url: String
    ) {
        fun toWaitingRoom(): WaitingRoom {
            return WaitingRoom(
                id = id,
                playera = player1,
                playerb = player2,
                variante = variante,
                openingRule = openingRule,

            )
        }
    }


