package Gomoku


import Gomoku.Http.GamesController
import Gomoku.Services.GamesService
import android.app.Application
import com.google.gson.Gson

import okhttp3.OkHttpClient

class GamesApplication : Application() {

    val httpClient: OkHttpClient =
        OkHttpClient.Builder()
            .build()

    val gson: Gson = Gson()

    val gamesService: GamesService = GamesController(httpClient, gson)

}