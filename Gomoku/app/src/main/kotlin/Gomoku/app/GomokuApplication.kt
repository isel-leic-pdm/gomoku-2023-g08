package Gomoku.app


import android.app.Application

import Gomoku.CreateGame.CreateGameAct
import Gomoku.DataStore.Domain.GameInfoDataStorage
import Gomoku.DataStore.Domain.GameInfoRepository

import Gomoku.DataStore.Domain.UserInfoRepository
import Gomoku.DataStore.Storage.UserInfoDataStore
import Gomoku.PlayGame.PlayGameAct
import Gomoku.User.GamesAct
import Gomoku.User.UsersToServer
import Gomoku.Rankings.RankingAct
import Gomoku.ReplayGames.ReplayGameAct
import Gomoku.Services.CreateGameService
import Gomoku.Services.GamesService
import Gomoku.Services.PlayGameService
import Gomoku.Services.UsersService
import Gomoku.User.UsersViewModel
import androidx.datastore.core.DataStore


import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson

import okhttp3.OkHttpClient
const val LINK = "https://ea38-2a01-14-130-a420-b133-c7d2-dcab-4202.ngrok-free.app"

interface DependenciesContainer {
    val userInfoRepository: UserInfoRepository
    val gameService: CreateGameService
    val playGameService: PlayGameService
}

class GomokuApplication() : Application(), DependenciesContainer {

    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_info")
    override val userInfoRepository: UserInfoRepository
        get() = UserInfoDataStore(dataStore)

    override val gameService: CreateGameService
        get() = CreateGameAct(httpClient, gson)
    override val playGameService: PlayGameService
        get() = PlayGameAct(httpClient, gson)


    val httpClient: OkHttpClient = OkHttpClient.Builder().build()
    val gson: Gson = Gson()
 //   val gamesService: GamesService = GamesAct(httpClient, gson)
    val usersService: UsersService = UsersToServer(httpClient, gson, )
    val rankingService= RankingAct(httpClient, gson)
    val createGameService= CreateGameAct(httpClient, gson)
    val saveGame = ReplayGameAct(httpClient, gson)


}