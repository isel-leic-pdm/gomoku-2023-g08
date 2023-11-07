package Gomoku.Rankings


import Gomoku.DomainModel.Users
import Gomoku.Services.FetchGameException
import Gomoku.Services.RankingService
import Gomoku.app.LINK


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
            .url("$LINK/rankings")
            .addHeader("accept", "application/vnd.siren+json")
            .build()
    }
    override suspend fun fetchRanking(): MutableList<UsersRankOutput> {
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
                        it.resume(mutableListOf(gson.fromJson(body.string(), UserRankDto::class.java).toUser()))
                }
            })
        }
    }
}




private data class UserRankDto(
    val username: String,
    val wins: Int,
    val rank : Int,
    val jogos: Int,
    val url: URL
) {
    fun toUser() = UsersRankOutput(username,wins,rank,jogos)


}


 /*
    override suspend fun fetchRanking(): MutableList<UsersRankOutput> {
        return suspendCoroutine { continuation ->
            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    continuation.resumeWithException(FetchGameException("Failed to create", e))
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null) {
                        continuation.resumeWithException(FetchGameException("Failed to create user: ${response.code}"))
                    } else {
                        val jsonString = body.string()
                        val jsonObject = gson.fromJson(jsonString, Any::class.java) as Map<*, *>

                        // Verificar se a estrutura do JSON está correta
                        if (jsonObject.containsKey("properties")) {
                            val properties = jsonObject["properties"] as List<Map<*, *>>?

                            if (properties != null) {
                                val usersRankOutputList = mutableListOf<UsersRankOutput>()

                                // Iterar sobre os elementos e mapear para o objeto desejado
                                for (property in properties) {
                                    val username = property["username"] as String
                                    val wins =
                                        (property["vitorias"] as Double).toInt() // Ajuste para obter o inteiro
                                    val rank =
                                        (property["ranking"] as Double).toInt() // Ajuste para obter o inteiro
                                    val games =
                                        (property["jogos"] as Double).toInt() // Ajuste para obter o inteiro

                                    val userRankOutput =
                                        UsersRankOutput(username, wins, rank, games)
                                    usersRankOutputList.add(userRankOutput)
                                }

                                continuation.resume(usersRankOutputList)
                            } else {
                                continuation.resumeWithException(FetchGameException("Failed to parse properties"))
                            }
                        } else {
                            continuation.resumeWithException(FetchGameException("Properties not found in response"))
                        }
                    }
                }
            })
        }
    }
}

*/
