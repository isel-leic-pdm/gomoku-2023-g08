package Gomoku.ReplayGames


import Gomoku.CreateGame.WaitingRoomViewModel
import Gomoku.DomainModel.Board
import Gomoku.DomainModel.GameReplayShowModel
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.variantes
import Gomoku.PlayGame.PlayGameScreen
import Gomoku.PlayGame.TAG1
import Gomoku.State.LoadedGameCreated
import Gomoku.State.LoadedSaveReplayGame
import Gomoku.State.loadSaveReplayGame
import Gomoku.app.DependenciesContainer
import Gomoku.app.GomokuApplication
import Gomoku.ui.BoardView
import android.annotation.SuppressLint


import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ShowReplayGameActivity() : ComponentActivity() {

    val app by lazy { application as GomokuApplication }
    private val vm by viewModels<ReplayGameViewModel> {
        ReplayGameViewModel.factory((application as DependenciesContainer).replayGameService, (application as DependenciesContainer).replayInfoRepository)
    }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, ShowReplayGameActivity::class.java)
            origin.startActivity(intent)
        }

/*
        fun navigateTo1(ctx: Context, game: List<GameReplayShowModel>) {
            ctx.startActivity(createIntent(ctx, game))
        }

        fun createIntent(ctx: Context, games: List<GameReplayShowModel>): Intent {
            val intent = Intent(ctx, ShowReplayGameActivity::class.java)
             intent.putExtra("games",games)
            return intent
        }
*/
    }

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
           Log.v("REPLAY", "before entering ${vm.gamedReplay}")
            setContent {
                       ShowReplayGameScreen(
                           onBackRequested = { finish() },
                           onPreviousRequested = vm::decrementMoveIndex,
                           onNextRequested = vm::incrementMoveIndex,
                           games = vm.gamedReplay
                           )


               }

       }
    }

    @Parcelize
    private data class GameShowModel(
        val game_id: Int,
        val player: Int,
        val turn: Int,
        val variant: variantes,
        val openingRule: openingrule,
        val line: Int,
        val col: Char,
       val board: @RawValue Board,
    ) : Parcelable {
        constructor(gameReplay: GameReplayShowModel) : this(
            gameReplay.game_id, gameReplay.player, gameReplay.turn, gameReplay.variant, gameReplay.openingRule, gameReplay.line,gameReplay.col, gameReplay.board

        )
    }




    override fun onStart() {
        super.onStart()
        Log.v(TAG, "AboutActivity.onStart() called")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "AboutActivity.onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "AboutActivity.onDestroy() called")
    }
}



/*
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
       vm.getGame()
        vm.startPolling()
               lifecycleScope.launch {
           vm.gameFlow.collectLatest {
               vm.getGame()
                setContent {
                     Log.v(TAG1, " setContent called")
                     val gameState by vm.gameFlow.collectAsState()

                     LaunchedEffect(gameState) {
                          if (gameState is LoadedGameCreated ) {
                            Log.v("GAMERES", "launchEffect: $gameState")
                          }
                         Log.v("GAMERES", "launchEffect outside if : $gameState")
                     }

                     PlayGameScreen(
                          onBackRequested = { finish() },
                          onPlayRequested = vm::play,
                          game = gameState
                     )
                }
           }
        }
    }

 */