package Gomoku.User

import Gomoku.DomainModel.Board

import Gomoku.DomainModel.Game
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.variantes

import Gomoku.Services.FetchGameException
import Gomoku.Services.GamesService
import android.util.Log
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GamesAct(
    private val client: OkHttpClient,
    private val gson: Gson
) : GamesService {
    private data class GameDto(val id: Int,
        val board: Board,
        val playerA: Int,
        val playerB: Int,
        val variante: variantes,
        val openingRule: openingrule,
        val winner: Int,
        val turn: Int,
    ) {
       fun toGame() = Game(id =id,board, playerA, playerB, variante, openingRule, winner, turn, url = URL("https://localhost:8080/games/matchMaking"))
    }


    private val request by lazy {
        Request.Builder()
            .url("https://localhost:8080/games/matchMaking")
            .addHeader("accept", "application/json")
            .build()
    }

    override suspend fun fetchGame(): Game {
        Log.v("GamesController", "fetchGame() called")
        return suspendCoroutine {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException(FetchGameException("Failed to fetch Game", e))
                }

                override fun onResponse(call: Call, response: Response) {
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