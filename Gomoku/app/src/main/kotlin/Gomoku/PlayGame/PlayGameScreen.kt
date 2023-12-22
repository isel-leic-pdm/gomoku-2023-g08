package Gomoku.PlayGame

import Gomoku.DomainModel.Cell
import Gomoku.State.LoadStateGameCreated
import Gomoku.State.LoadedGameCreated
import Gomoku.ui.BoardView
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi", "StateFlowValueCalledInComposition")
@Composable
fun PlayGameScreen(
    onBackRequested: () -> Unit,
    onPlayRequested: (Int, Int) -> Unit,
    game: LoadStateGameCreated,
) {
    if (game is LoadedGameCreated) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            // Top Bar
            TopAppBar(
                title = { Text(text = "Gomoku Game", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackRequested) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Text(
                        text = "Turn: ${game.result.getOrNull()?.turn}",
                        modifier = Modifier.padding(end = 16.dp),
                        fontWeight = FontWeight.Bold
                    )
                },

            )

            // Game Board
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                val gameRes = game.result.getOrNull()
                if (gameRes != null) {
                    BoardView(
                        board = gameRes.board,
                        variante = game.result.getOrNull()?.variante,
                        onclick = onPlayRequested
                    )
                }
            }
        }
    } else {
        // Loading State
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
