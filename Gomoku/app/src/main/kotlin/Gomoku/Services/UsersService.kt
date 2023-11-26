package Gomoku.Services

import Gomoku.DomainModel.Users
import Gomoku.DomainModel.UsersFake


interface UsersService {

  //  suspend fun getUserID(username: String, password: String): Int
    suspend fun getAuthToken(id:Int): String

    suspend fun createuser(username: String, password: String, usersService: UsersService): Users
    suspend fun loginuser(username: String,password: String): Users
    suspend fun logout(id:Int): Unit
}
interface UsersServiceFake {

    suspend fun createuser(username: String, password: String, usersService: UsersService): UsersFake
    suspend fun loginuser(): UsersFake
}


class FetchUserException(message: String, cause: Throwable? = null)
    : Exception(message, cause)


