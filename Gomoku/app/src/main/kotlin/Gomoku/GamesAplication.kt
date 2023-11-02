package Gomoku


import Gomoku.Http.GamesAct
import Gomoku.Http.UsersAct
import Gomoku.Rankings.RankingAct
import Gomoku.Services.GamesService
import Gomoku.Services.UsersService
import android.app.Application
import com.google.gson.Gson

import okhttp3.OkHttpClient

class Application : Application() {

    val httpClient: OkHttpClient =
        OkHttpClient.Builder()
            .build()

    val gson: Gson = Gson()


    val gamesService: GamesService = GamesAct(httpClient, gson)
    val usersService: UsersService = UsersAct(httpClient, gson)
    val rankingService= RankingAct(httpClient, gson)

}