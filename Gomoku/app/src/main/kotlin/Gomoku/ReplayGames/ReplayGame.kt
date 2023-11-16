package Gomoku.ReplayGames


import Gomoku.DomainModel.Board
import Gomoku.Services.FetchGameException
import Gomoku.Services.RankingService
import Gomoku.Services.ReplayGameInterface
import Gomoku.State.ReplayGameModel
import Gomoku.app.LINK
import android.util.Log


import com.google.gson.Gson

import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ReplayGameAct(
    val client: OkHttpClient,
    val gson: Gson
) : ReplayGameInterface {
    val idGame = ReplayGameViewModel().idGame
    private val request by lazy {
        Request.Builder()
            .url("$LINK/games/replay/$idGame")
            .addHeader("accept", "application/vnd.siren+json")
            .build()
    }

    override suspend fun fetchReplayGame(id: Int): List<ReplayGameModel> {
        Log.v("EIU","id : "+ idGame.toString())
        Log.v("EIU","id : "+ id.toString())
        return suspendCoroutine { continuation ->
            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    Log.v("EIU","error : "+ e.toString())
                    continuation.resumeWithException(FetchGameException("Failed to create", e))
                }


                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    val body = response.body
                    Log.v("EIU","body : "+ body.toString())
                    if (!response.isSuccessful || body == null) {
                        continuation.resumeWithException(FetchGameException("Failed to create user: ${response.code}"))
                    } else {
                        val jsonString = body.string()
                        Log.v("EIU","jsonString : "+ jsonString.toString())
                        val jsonObject = gson.fromJson(jsonString, Map::class.java)
                        Log.v("EIU","jsonObject : "+ jsonObject.toString())
                        val values = transform(jsonObject)
                        Log.v("EIU","values : "+ values.toString())

                        continuation.resume(values)
                    }
                }
            })
        }
    }

    /*
    override suspend fun fetchRanking(): List<UsersRankOutput> {
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
                        val jsonObject = gson.fromJson(jsonString, Map::class.java)
                        val values = transform(jsonObject)
                        Log.v("RANKING","values : "+ values.toString())

                        continuation.resume(values)
                    }
                }
            })
        }
    }

}

     */

fun transform(jsonObject: Map<*, *>): List<ReplayGameModel> {
    val listToReturn = mutableListOf<ReplayGameModel>()
    if (jsonObject.containsKey("properties")) {
        val properties = jsonObject["properties"] as? List<Map<*, *>>
        properties?.forEach { property ->
         val game_id = property["game_id"]
            val player = property["player"]
            val turn = property["turn"]
            val line = property["line"]
            val col = property["col"]
            val board = property["board"]

            if (game_id != null && player != null && turn != null && line != null && col != null) {
                val ReplaGameDto = ReplayGameModel(
                    (game_id as Double).toInt(),
                    (player as Double).toInt(),
                    (turn as Double).toInt(),
                    (line as Double).toInt(),
                    col.toString(),
                    board.toString()
                )
                Log.v("EIU","ReplaGameDto : "+ ReplaGameDto.toString())
                listToReturn.add(ReplaGameDto)

            }

            }
        }
    return listToReturn
    }
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
