package Gomoku.Http

import Gomoku.DomainModel.Board
import Gomoku.DomainModel.Game
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.variantes
import Gomoku.Services.FetchGameException

import Gomoku.Services.GamesService

import com.google.gson.Gson

import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GamesController(
    private val client: OkHttpClient,
    private val gson: Gson
) : GamesService {

    private val request by lazy {
        Request.Builder()
            .url("https://localhost:8080/games/matchMaking")
            .addHeader("accept", "application/json")
            .build()
    }

    override suspend fun fetchGame(): Game {
        return suspendCoroutine {
            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    it.resumeWithException(FetchGameException("Failed to fetch Game", e))
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null)
                        it.resumeWithException(FetchGameException("Failed to fetch Game: ${response.code}"))
                    else
                        it.resume(gson.fromJson(body.string(), GameDto::class.java).toGame())
                }
            })
        }
    }
}




private data class GameDto(
    val board: Board,
    val playerA: Int,
    val playerB: Int,
    val variante: variantes,
    val openingRule: openingrule,
    val winner: Int,
    val turn: Int,
) {
    fun toGame() = Game(board, playerA, playerB, variante, openingRule, winner, turn, url = URL("https://localhost:8080/games/matchMaking"))
}



