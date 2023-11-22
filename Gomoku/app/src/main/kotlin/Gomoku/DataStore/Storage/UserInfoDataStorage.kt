package Gomoku.DataStore.Storage

import Gomoku.DataStore.Domain.UserInfo
import Gomoku.DataStore.Domain.UserInfoRepository
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first



private const val USER_ID = "id"
private const val USER_NAME = "username"
private const val USER_PASS = "password"
private const val USER_TOKEN = "token"


class UserInfoDataStore(private val store: DataStore<Preferences>) : UserInfoRepository {

    private val user_id = stringPreferencesKey(USER_ID)
    val user_name = stringPreferencesKey(USER_NAME)
    private val user_pass = stringPreferencesKey(USER_PASS)
    private val user_token = stringPreferencesKey(USER_TOKEN)

    override suspend fun getUserInfo(): UserInfo? {
        val preferences = store.data.first()
        val userID = preferences[user_id]?.toInt()
        val username = preferences[user_pass]
        val userPass = preferences[user_pass]
        val userToken = preferences[user_token]
        return if (userID != null) UserInfo(userID,username,  userPass, userToken ) else null
    }

    override suspend fun updateUserInfo(userInfo: UserInfo) {
        store.edit {  preferences ->
            preferences[user_id] = userInfo.id.toString()
            userInfo.username?.let {
                preferences[user_id] = it
            } ?: preferences.remove(user_id)

        }
    }
}





