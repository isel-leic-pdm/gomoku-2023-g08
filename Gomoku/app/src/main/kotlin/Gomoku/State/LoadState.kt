package Gomoku.State

import Gomoku.DomainModel.Game


sealed class LoadState

 object Idle : LoadState()
 object Loading : LoadState()
data class Loaded(val result: Result<Game>) : LoadState()