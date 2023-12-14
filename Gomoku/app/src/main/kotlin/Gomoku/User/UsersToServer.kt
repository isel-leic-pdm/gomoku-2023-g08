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

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody




class UsersToServer(
    val client: OkHttpClient,
    val gson: Gson,

) : UsersService {

    fun transform(jsonObject: Map<*, *>, username: String, password: String):Users {
        if (jsonObject.containsKey("properties")) {
            val properties = jsonObject["properties"] as? Map<*, *>
            Log.v("12345", "inside transform: " + properties)
            val id = properties?.get("id") as? Double
            val id1 = id?.toInt()
            val token = properties?.get("token") as? String
            return Users(id1, username, password, token, url = URL("$LINK/users/login"))
        }
        return Users(null, null, null, null, url = URL("$LINK/users/login"))

    }

    override suspend fun getAuthToken(id: Int): String {
        val json = gson.toJson(mapOf("id" to id))
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
                    if (!response.isSuccessful || body == null) {
                        Log.v("12345", "erro no id")
                        continuation.resumeWithException(FetchGameException("Failed to create user : ${response.code}"))
                    } else
                        continuation.resume(gson.fromJson(body.string(), UserDto::class.java).token)
                }
            })
        }
    }


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
                    Log.v("USERNAME","onFailure" + e.toString() )
                    continuation.resumeWithException(FetchUser1Exception("Failed to create", e))
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.v("USERNAME","respnse CALLED" + response.code )
                    val body = response.body
                    if (!response.isSuccessful || body == null) {
                   continuation.resume(Users(null,"","","",  url = URL("$LINK/users/login")))

                        Log.v("USERNAME","erro no Login" + response.code )
                    }  else {
                        val jsonString = body.string()
                        val jsonObject = gson.fromJson(jsonString,Map::class.java)
                        Log.v("USERNAME","sucesso no Login- object" + response.code + "--" + jsonObject.toString() )
                        val values = transform(jsonObject, username, password)
                        Log.v("USERNAME","sucesso no Login" + response.code + "--" + values.toString() )
                        continuation.resume(values)

                    }
                }
            })
        }



    }

    override suspend fun logout(id: Int?): Unit {
        val json = gson.toJson(mapOf("id" to id))
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)


        val request = Request.Builder()
            .url("$LINK/users/logout/$id")
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
                        continuation.resume(gson.fromJson(body.string(), Unit::class.java))
                }
            })
        }
    }

    private data class UserDto(
        val id: Int,
        val username : String,
        val password : String,
        val token: String
    ) {
        fun toUser() = Users(id,username,password,token,  url = URL("$LINK/users/register"))
        fun toUserLog(): Users = Users(id,username,password,token,  url = URL("$LINK/users/login"))
    }
}




