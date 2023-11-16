package Gomoku.app


import android.app.Application

import Gomoku.CreateGame.CreateGameAct
import Gomoku.User.GamesAct
import Gomoku.User.UsersToServer
import Gomoku.Rankings.RankingAct
import Gomoku.ReplayGames.ReplayGameAct
import Gomoku.Services.GamesService
import Gomoku.Services.UsersService
import com.google.gson.Gson

import okhttp3.OkHttpClient
const val LINK = "https://8558-194-210-187-189.ngrok.io"
class GomokuApplication : Application()  {
    val httpClient: OkHttpClient = OkHttpClient.Builder().build()
    val gson: Gson = Gson()
    val gamesService: GamesService = GamesAct(httpClient, gson)
    val usersService: UsersService = UsersToServer(httpClient, gson)
    val rankingService= RankingAct(httpClient, gson)
    val createGameService= CreateGameAct(httpClient, gson)
    val saveGame = ReplayGameAct(httpClient, gson)

}