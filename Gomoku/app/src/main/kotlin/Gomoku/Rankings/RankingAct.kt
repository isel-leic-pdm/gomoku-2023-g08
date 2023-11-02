package Gomoku.Rankings


import Gomoku.DomainModel.Users
import Gomoku.Services.FetchGameException
import Gomoku.Services.RankingService

import Gomoku.Services.UsersService

import com.google.gson.Gson

import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RankingAct(
    val client: OkHttpClient,
    val gson: Gson
) : RankingService {


    private val request by lazy {
        Request.Builder()
            .url(" https://94f0-194-210-191-198.ngrok.io/rankings")
            .addHeader("accept", "application/json")
            .build()
    }

    override suspend fun fetchRanking(): List<Users> {
        return suspendCoroutine {
            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    it.resumeWithException(FetchGameException("Failed to create", e))
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null)
                        it.resumeWithException(FetchGameException("Failed to create user : ${response.code}"))
                    else
                        it.resume(listOf(gson.fromJson(body.string(), UserRankDto::class.java).toUser()))
                }
            })
        }
    }
}




private data class UserRankDto(
    val username : String,
    val password : String,
    val token: String
) {
    fun toUser() = Users(username,password,  url = URL("  https://94f0-194-210-191-198.ngrok.io/rankings"))
}



