package Gomoku.DataStore.Domain

interface UserInfoRepository {

    suspend fun getUserInfo(): UserInfo?
    suspend fun updateUserInfo(userInfo: UserInfo)
}