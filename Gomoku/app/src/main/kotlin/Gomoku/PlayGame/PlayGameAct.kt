package Gomoku.PlayGame

import Gomoku.DomainModel.Board
import Gomoku.DomainModel.Game
import Gomoku.DomainModel.Models.WaitingRoom
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.variantes

import Gomoku.Services.CreateGameService
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
import java.net.URL

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PlayGameAct(
    val client: OkHttpClient,
    val gson: Gson
) : PlayGameService {

    override suspend fun play(id: Int, line: Int, col: Int, authToken: String, idGame: Int): Game? {
        val json = gson.toJson(mapOf("userId" to id, "l" to line, "c" to col))
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)


       val request by lazy {
            Request.Builder()
                .url("$LINK/games/play/$idGame")
                .addHeader("Authorization", "Bearer $authToken")
                .addHeader("accept", "application/json")
                .post(requestBody)
                .build()
        }
        return suspendCoroutine {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException(FetchUser1Exception("Failed to fetch User", e))
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null)
                        it.resumeWithException(FetchUser1Exception("Failed to fetch User: ${response.code}"))
                    else
                        it.resume(gson.fromJson(body.string(), GameDto::class.java).toGame())
                    Log.v("aaaa", "PlayGameAct.onCreate() called ${it.resume(gson.fromJson(body?.string(), GameDto::class.java).toGame())}")
                }
            })
        }
    }


    private data class GameDto(
        val id: Int,
        val board: Board,
        val playerA: Int,
        val playerB: Int,
        val variante: variantes,
        val openingRule: openingrule,
        val winner: Int,
        val turn: Int,
    ) {
        fun toGame() = Game(id= 12,board, playerA, playerB, variante, openingRule, winner, turn, url = URL("https://localhost:8080/games/matchMaking"))
    }
}



