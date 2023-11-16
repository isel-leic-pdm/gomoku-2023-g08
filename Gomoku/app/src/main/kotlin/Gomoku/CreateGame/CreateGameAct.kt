package Gomoku.CreateGame


import Gomoku.DomainModel.Game
import Gomoku.DomainModel.Models.WaitingRoom
import Gomoku.DomainModel.openingrule
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
val authToken1 = "855039a4-226a-4ed1-ac5e-3a0814a231fc"
val authToken2 = "855039a4-226a-4ed1-ac5e-3a0814a231fc"
class CreateGameAct(
    val client: OkHttpClient,
    val gson: Gson
) : CreateGameService {
    override suspend fun getGame(id: Int): Game? {
        val request = Request.Builder()
            .url("$LINK/games/$id")
            .get()
            .build()

        return suspendCoroutine { continuation ->
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(FetchUser1Exception("Failed to create", e))
                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        if (!response.isSuccessful) {
                            throw IOException("Unexpected HTTP code: $response")
                        }

                        val responseBody = response.body
                        if (responseBody != null) {
                            val gameJson = responseBody.string()
                            val game = gson.fromJson(gameJson, Game::class.java)

                            continuation.resume(game)
                        } else {
                            throw IOException("Response body is null")
                        }
                    } catch (e: Exception) {
                        continuation.resumeWithException(e)
                    } finally {
                        response.close()
                    }
                }
            })
        }
    }


    override suspend fun fetchCreateGame(
        idPlayer: Int?,
        openingrule: openingrule?,
        variantes: variantes?,
        authToken: String
    ): WaitingRoom {
        val requestBodyJson = gson.toJson(mapOf("player" to idPlayer, "openingRule" to openingrule, "variante" to variantes))
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
}

