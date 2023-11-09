package Gomoku.User

import Gomoku.DomainModel.Users

import Gomoku.Services.FetchGameException
import Gomoku.Services.FetchUser1Exception
import Gomoku.Services.UsersService
import Gomoku.app.LINK
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

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody




class UsersToServer(
    val client: OkHttpClient,
    val gson: Gson
) : UsersService {


    override suspend fun createuser(username: String, password: String, usersService: UsersService): Users {
        val json = gson.toJson(mapOf("username" to username, "password" to password))

        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)

        val request = Request.Builder()
            .url("$LINK/users/register")
            .addHeader("Accept", "application/vnd.siren+json")
            .post(requestBody)
            .build()

        return suspendCoroutine { continuation ->
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(FetchUser1Exception("Failed to create", e))
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null)
                        continuation.resumeWithException(FetchGameException("Failed to create user : ${response.code}"))
                    else
                        continuation.resume(gson.fromJson(body.string(), UserDto::class.java).toUser())
                }
            })
        }
    }

    override suspend fun loginuser(username: String,password: String): Users {
        val json = gson.toJson(mapOf("username" to username, "password" to password))
        Log.v("USERNAME",username)
        Log.v("USERNAME",password)

        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)

        val request = Request.Builder()
            .url("$LINK/users/login")
            .addHeader("Accept", "application/vnd.siren+json")
            .post(requestBody)
            .build()

        return suspendCoroutine { continuation ->
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(FetchUser1Exception("Failed to create", e))
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null)
                        continuation.resumeWithException(FetchGameException("Failed to create user : ${response.code}"))
                    else
                        continuation.resume(gson.fromJson(body.string(), UserDto::class.java).toUserLog())
                }
            })
        }


    }

    private data class UserDto(
        val username : String,
        val password : String,
        val token: String
    ) {
        fun toUser() = Users(username,password,token,  url = URL("$LINK/users/register"))
        fun toUserLog() = Users(username,password,token,  url = URL("$LINK/users/login"))
    }
}




