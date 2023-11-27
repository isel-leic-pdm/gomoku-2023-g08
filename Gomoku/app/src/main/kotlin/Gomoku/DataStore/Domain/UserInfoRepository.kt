package Gomoku.DataStore.Domain

import Gomoku.DomainModel.Models.WaitingRoom

interface UserInfoRepository {

    suspend fun getUserInfo(): UserInfo?
    suspend fun updateUserInfo(userInfo: UserInfo)

    suspend fun getWaitingRoom(): WaitingRoom?
    suspend fun updateWaitingRoom(waitingRoom: WaitingRoom)
    suspend fun getCurrentGame(): Int
    suspend fun updateCurrentGame(id: Int)


}

