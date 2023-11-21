package Gomoku.DataStore.Storage

import Gomoku.DataStore.Domain.UserInfo
import Gomoku.DataStore.Domain.UserInfoRepository
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import java.util.prefs.Preferences


private const val USER_ID = "id"
private const val USER_PASS = "password"
private const val USER_TOKEN = "token"

/*
/**
 * A user information repository implementation supported in DataStore, the
 * modern alternative to SharedPreferences.
 */
class UserInfoDataStore(private val store: DataStore<Preferences>) : UserInfoRepository {

    private val user_id = stringPreferencesKey(USER_ID)
    private val user_pass = stringPreferencesKey(USER_PASS)
    private val user_token = stringPreferencesKey(USER_TOKEN)

    override suspend fun getUserInfo(): UserInfo? {
        val preferences = store.data.first()
        val userID = preferences[user_id]
        val userPass = preferences[user_pass]
        val userToken = preferences[user_token]
        return if (userID != null) UserInfo(userID) else null
    }

    override suspend fun updateUserInfo(userInfo: UserInfo) {
        store.edit { preferences ->
            preferences[user_id] = userInfo.nick
            userInfo.motto?.let {
                preferences[user_token] = it
            } ?: preferences.remove(user_token)
        }
    }
}

 */



