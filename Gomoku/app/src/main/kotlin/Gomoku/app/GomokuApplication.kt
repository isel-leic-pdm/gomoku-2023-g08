package Gomoku.app


import android.app.Application

import Gomoku.CreateGame.CreateGameAct
import Gomoku.User.GamesAct
import Gomoku.User.UsersToServer
import Gomoku.Rankings.RankingAct
import Gomoku.Services.GamesService
import Gomoku.Services.UsersService
import com.google.gson.Gson

import okhttp3.OkHttpClient
const val LINK = "https://d233-2001-8a0-70a6-7900-4088-4776-c381-fe2b.ngrok.io "
class GomokuApplication : Application()  {
    val httpClient: OkHttpClient = OkHttpClient.Builder().build()
    val gson: Gson = Gson()
    val gamesService: GamesService = GamesAct(httpClient, gson)
    val usersService: UsersService = UsersToServer(httpClient, gson)
    val rankingService= RankingAct(httpClient, gson)
    val createGameService= CreateGameAct(httpClient, gson)

}