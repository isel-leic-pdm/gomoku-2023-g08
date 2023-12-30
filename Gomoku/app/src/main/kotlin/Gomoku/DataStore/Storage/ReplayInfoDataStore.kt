package Gomoku.DataStore.Storage



import Gomoku.DataStore.Domain.GameID
import Gomoku.DataStore.Domain.ReplayInfoRepository
import Gomoku.DataStore.Domain.UserInfo
import Gomoku.DataStore.Domain.UserInfoRepository
import Gomoku.DomainModel.Models.WaitingRoom
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first



private const val game_ID = "game_id"



class ReplayInfoDataStore(private val store: DataStore<Preferences>) : ReplayInfoRepository {
    private val game_id = stringPreferencesKey(game_ID)

    override suspend fun getReplayId(): Int {
        val preferences = store.data.first()
        val storedId = preferences[game_id]?.toInt() ?: 0
        return storedId

    }

    override suspend fun rightReplayId(id: Int) {
        store.edit { preferences ->
            Log.v("ENTREI", "editing: = ${preferences[game_id]}")
            id?.let { preferences[game_id] = it.toString() }

        }
    }


}





