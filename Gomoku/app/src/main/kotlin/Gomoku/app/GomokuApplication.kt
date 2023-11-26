package Gomoku.app


import android.app.Application

import Gomoku.CreateGame.CreateGameAct

import Gomoku.DataStore.Domain.UserInfoRepository
import Gomoku.DataStore.Storage.UserInfoDataStore
import Gomoku.PlayGame.PlayGameAct
import Gomoku.User.GamesAct
import Gomoku.User.UsersToServer
import Gomoku.Rankings.RankingAct
import Gomoku.ReplayGames.ReplayGameAct
import Gomoku.Services.GamesService
import Gomoku.Services.PlayGameService
import Gomoku.Services.UsersService
import Gomoku.User.UsersViewModel
import androidx.datastore.core.DataStore


import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson

import okhttp3.OkHttpClient
const val LINK = " https://9276-2001-8a0-70a6-7900-685a-9fef-18b9-1ec8.ngrok.io"

interface DependenciesContainer {
    val userInfoRepository: UserInfoRepository
}

class GomokuApplication : Application(), DependenciesContainer {

    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_info")


    override val userInfoRepository: UserInfoRepository
        get() = UserInfoDataStore(dataStore)

    val httpClient: OkHttpClient = OkHttpClient.Builder().build()
    val gson: Gson = Gson()
 //   val gamesService: GamesService = GamesAct(httpClient, gson)
    val playGameService: PlayGameService = PlayGameAct(httpClient, gson)
    val usersService: UsersService = UsersToServer(httpClient, gson, )
    val rankingService= RankingAct(httpClient, gson)
    val createGameService= CreateGameAct(httpClient, gson)
    val saveGame = ReplayGameAct(httpClient, gson)


}