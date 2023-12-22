    package Gomoku.DataStore.Storage

    import Gomoku.DataStore.Domain.UserInfo
    import Gomoku.DataStore.Domain.UserInfoRepository
    import Gomoku.DomainModel.Models.WaitingRoom
    import android.util.Log
    import androidx.datastore.core.DataStore
    import androidx.datastore.preferences.core.Preferences
    import androidx.datastore.preferences.core.edit
    import androidx.datastore.preferences.core.stringPreferencesKey
    import kotlinx.coroutines.flow.first



    private const val USER_ID = "id"
    private const val USER_NAME = "username"
    private const val USER_PASS = "password"
    private const val USER_TOKEN = "token"
    private const val CURRENT_GAME = "current_game"


    class UserInfoDataStore(private val store: DataStore<Preferences>) : UserInfoRepository {


        private val user_id = stringPreferencesKey(USER_ID)
        val user_name = stringPreferencesKey(USER_NAME)
        private val user_pass = stringPreferencesKey(USER_PASS)
        private val user_token = stringPreferencesKey(USER_TOKEN)
        private val current_game = stringPreferencesKey(CURRENT_GAME)
        override suspend fun getUserInfo():UserInfo {


            val preferences = store.data.first()

            val storedId = preferences[user_id]?.toInt()
            val storedUsername = preferences[user_name]
            val storedPassword = preferences[user_pass]
            val storedToken = preferences[user_token]

            Log.v("123456", "get = $storedId, $storedUsername, $storedPassword, $storedToken")

            return UserInfo(storedId!!, storedUsername, storedPassword, storedToken)
        }

        override suspend fun clearCurrentGame() {
            store.edit { preferences ->
                preferences[current_game] = 0.toString()
            }
        }

        override suspend fun updateUserInfo(userInfo: UserInfo) {
            Log.v("ENTREI", "update = $userInfo")

            store.edit { preferences ->
                Log.v("ENTREI", "editing = $userInfo")
                Log.v("ENTREI", "editing: = ${preferences[user_id]}")
                Log.v("ENTREI", "editing: = ${preferences[user_name]}")
                Log.v("ENTREI", "editing: = ${preferences[user_pass]}")
                Log.v("ENTREI", "editing: = ${preferences[user_token]}")
                userInfo.id?.let { preferences[user_id] = it.toString() }
                userInfo.username?.let { preferences[user_name] = it }
                userInfo.password?.let { preferences[user_pass] = it }
                userInfo.token?.let { preferences[user_token] = it }
            }
            Log.v("ENTREI", "editing: = ${store.data.first()}")

        }

        override suspend fun getWaitingRoom(): WaitingRoom? {
            TODO("Not yet implemented")
        }

        override suspend fun updateWaitingRoom(waitingRoom: WaitingRoom) {
            TODO("Not yet implemented")
        }

        override suspend fun getCurrentGame(): Int {
            Log.v("ENTREI", "GetCurrGame = $current_game")
            val preferences = store.data.first()
            val storedId = preferences[current_game]?.toInt()
            Log.v("ENTREI", "updateCurrGame = $storedId")

            return storedId!!
        }

        override suspend fun updateCurrentGame(id: Int) {
            Log.v("ENTREI", "updateCuurGame = $id")
            store.edit { preferences ->
                preferences[current_game] = id.toString()
            }
        }

    }





