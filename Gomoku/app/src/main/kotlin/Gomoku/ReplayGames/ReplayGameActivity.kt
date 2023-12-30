package Gomoku.ReplayGames


import Gomoku.app.DependenciesContainer
import Gomoku.app.GomokuApplication
import android.annotation.SuppressLint


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

class ReplayGameActivity: ComponentActivity() {
    val app by lazy { application as GomokuApplication }
    private val vm by viewModels<ReplayGameViewModel> {
        ReplayGameViewModel.factory((application as DependenciesContainer).replayGameService, (application as DependenciesContainer).replayInfoRepository, (application as DependenciesContainer).userInfoRepository)
    }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, ReplayGameActivity::class.java)
            origin.startActivity(intent)
        }
    }

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReplayGameScreen(
                onBackRequested = { finish() },
                fetchReplayGame = {
                    // Agora, você pode chamar vm.getGameSaved diretamente
                    vm.getGameSaved()
                },
                index = vm.moveIndex,
                fetchGamesPlayed = {
                    vm.fetchListIds()
                },

                setIdGame = vm::setIdGames,
                onPreviousRequested = { vm.decrementMoveIndex() },
                onNextRequested = { vm.incrementMoveIndex() },
                game = vm.gamedReplay,
                listIds = vm.ListIds,
            )
        }    }
}
