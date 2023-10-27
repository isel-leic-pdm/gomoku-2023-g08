package Gomoku.Http
import Gomoku.DomainModel.Users
import Gomoku.Services.FetchGameException

import Gomoku.Services.UsersService

import com.google.gson.Gson

import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UsersAct(
  val client: OkHttpClient,
     val gson: Gson
) : UsersService {


    private val request by lazy {
        Request.Builder()
            .url("https://localhost:8080/auth/CreateUser")
            .addHeader("accept", "application/json")
            .build()
    }

    override suspend fun createuser(username: String, password: String, usersService: UsersService): Users {
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
                        it.resume(gson.fromJson(body.string(), UserDto::class.java).toUser())
                }
            })
        }
    }





    override suspend fun loginuser(): Users {
        return suspendCoroutine {
            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    it.resumeWithException(FetchGameException("Failed to create", e))
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null)
                        it.resumeWithException(FetchGameException("Failed to fetch Game: ${response.code}"))
                    else
                        it.resume(gson.fromJson(body.string(), UserDto::class.java).toUser())
                }
            })
        }
    }
}




private data class UserDto(
   val username : String,
    val password : String,
    val token: String
) {
    fun toUser() = Users(username,password,  url = URL(" https://3b5b-2001-818-dca4-4600-2421-e71a-77-5e52.ngrok.io/auth/createUser"))
}



