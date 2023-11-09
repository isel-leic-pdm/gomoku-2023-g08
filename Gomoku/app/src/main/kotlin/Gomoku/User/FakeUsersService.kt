package Gomoku.User

import Gomoku.DomainModel.Users
import Gomoku.DomainModel.UsersFake
import Gomoku.Services.UsersService
import Gomoku.Services.UsersServiceFake
import android.util.Log
import kotlinx.coroutines.delay
import java.net.URL
import kotlin.random.Random

val users = mutableListOf<UsersFake>()
class FakeUsersService : UsersServiceFake {
    override suspend fun createuser(
        username: String,
        password: String,
        usersService: UsersService
    ): UsersFake {
        users.add(UsersFake(username, password,))
        Log.v("USERS", "$users")
        return UsersFake(username, password)
    }

    override suspend fun loginuser(): UsersFake {
        Log.v("USERS", "loginuser() called")
        delay(5000)
        val index = Random.nextInt(0, users.size - 1)
        Log.v("USERS", "Got user")
        return users[index]
    }


}