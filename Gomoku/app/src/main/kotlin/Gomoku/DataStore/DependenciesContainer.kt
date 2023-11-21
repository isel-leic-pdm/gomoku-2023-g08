package Gomoku.DataStore

import Gomoku.DataStore.Domain.UserInfoRepository

interface DependenciesContainer {
    val userInfoRepository: UserInfoRepository
}