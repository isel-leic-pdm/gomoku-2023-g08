package Gomoku.app


import android.app.Application

import Gomoku.CreateGame.CreateGameAct
import Gomoku.DataStore.DependenciesContainer
import Gomoku.DataStore.Domain.UserInfoRepository
import Gomoku.PlayGame.PlayGameAct
import Gomoku.User.GamesAct
import Gomoku.User.UsersToServer
import Gomoku.Rankings.RankingAct
import Gomoku.ReplayGames.ReplayGameAct
import Gomoku.Services.GamesService
import Gomoku.Services.PlayGameService
import Gomoku.Services.UsersService
import com.google.gson.Gson

import okhttp3.OkHttpClient
const val LINK = "https://7cc4-2001-8a0-70a6-7900-31c9-c5f-10a5-cf64.ngrok.io"
class GomokuApplication : Application(){
    val httpClient: OkHttpClient = OkHttpClient.Builder().build()
    val gson: Gson = Gson()
 //   val gamesService: GamesService = GamesAct(httpClient, gson)
    val playGameService: PlayGameService = PlayGameAct(httpClient, gson)
    val usersService: UsersService = UsersToServer(httpClient, gson)
    val rankingService= RankingAct(httpClient, gson)
    val createGameService= CreateGameAct(httpClient, gson)
    val saveGame = ReplayGameAct(httpClient, gson)


}