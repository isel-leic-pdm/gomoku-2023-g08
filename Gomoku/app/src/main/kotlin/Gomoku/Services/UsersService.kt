package Gomoku.Services

import Gomoku.DomainModel.Users
import Gomoku.Http.UsersAct
import com.google.gson.Gson
import okhttp3.OkHttpClient


interface UsersService {

    suspend fun createuser(username: String, password: String, usersService: UsersService): Users
    suspend fun loginuser(): Users
}

class FetchUserException(message: String, cause: Throwable? = null)
    : Exception(message, cause)


