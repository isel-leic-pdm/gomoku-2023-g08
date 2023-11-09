package Gomoku.CreateGame


import Gomoku.DomainModel.GamePlayer
import Gomoku.DomainModel.Models.WaitingRoom
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.variantes

import Gomoku.Services.CreateGameService
import Gomoku.Services.FetchGameException
import Gomoku.app.LINK

import android.util.Log

import com.google.gson.Gson

import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import kotlin.coroutines.Continuation

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CreateGameAct(
    val client: OkHttpClient,
    val gson: Gson
) : CreateGameService {


    private val request by lazy {
        Request.Builder()
            .url("$LINK/matchMaking")
            .addHeader("accept", "application/vnd.siren+json")
            .build()
    }

    override suspend fun fetchCreateGame(
        id: Int,
        openingrule: openingrule,
        variantes: variantes
    ): WaitingRoom {



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
                        it.resume(gson.fromJson(body.string(), waitR::class.java).toWaitingRoom())
                }
            })
        }
    }


    private data class waitR(
        val id: Int,
        val variantes: variantes,
        val openingRule: openingrule,
    ) {
        fun toWaitingRoom(): WaitingRoom = WaitingRoom(id,null, variante = variantes, openingRule = openingRule)



    }
}




