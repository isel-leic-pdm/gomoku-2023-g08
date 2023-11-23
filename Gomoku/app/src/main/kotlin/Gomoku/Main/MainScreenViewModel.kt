package Gomoku.Main

import Gomoku.DataStore.Domain.UserInfo
import Gomoku.DataStore.Domain.UserInfoRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(private val repository: UserInfoRepository) : ViewModel() {

    private val _userInfoFlow: MutableStateFlow<LoadState<UserInfo?>> = MutableStateFlow(idle())
    val userInfoFlow: Flow<LoadState<UserInfo?>>
        get() = _userInfoFlow.asStateFlow()


    }



