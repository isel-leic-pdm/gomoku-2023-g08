package Gomoku.Rankings




import Gomoku.DomainModel.Users
import Gomoku.Services.RankingService
import Gomoku.Services.UsersService
import Gomoku.State.IdleUser
import Gomoku.State.IdleUserRank
import Gomoku.State.LoadStateUser
import Gomoku.State.LoadStateUserRank
import Gomoku.State.LoadedUser
import Gomoku.State.LoadedUserRank
import Gomoku.State.LoadingUser
import Gomoku.State.LoadingUserRank

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class RankingViewModel() : ViewModel() {
    var ranking by mutableStateOf<LoadStateUserRank>(IdleUserRank)
        private set
   fun getRanking(serviceuser: RankingService): Unit {
        viewModelScope.launch {
            ranking = LoadingUserRank
            ranking = LoadedUserRank(
                runCatching {
                    serviceuser.fetchRanking()
                }
            )
        }
    }

}














