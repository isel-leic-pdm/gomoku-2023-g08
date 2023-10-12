package Gomoku.ui

import Gomoku.model.ViewModel
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gomoku.R
import Gomoku.Main.boardSize
import Gomoku.Main.cellSize
import pt.isel.tds.gomoku.model.BOARD_DIM
import pt.isel.tds.gomoku.model.Board
import pt.isel.tds.gomoku.model.BoardWin
import pt.isel.tds.gomoku.model.Cell
import pt.isel.tds.gomoku.model.Player

@Composable
fun BoardView(vm: ViewModel, board: Board?, onclick: (Cell) -> Unit ) {
    if(board == null){
    Log.v( "BoardView", "BoardView called with board: $board")
        Column(
            modifier = Modifier
                .size(boardSize)
                .background(Color.Black),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            repeat(BOARD_DIM) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    repeat(BOARD_DIM) { col ->
                        val pos = Cell(row, col)
                        CellView(
                                player = null,
                                onClick = { onclick(pos) },
                            )
                    }
                }
            }
        }
    }
    else{
        Column(
            modifier = Modifier
                .size(boardSize)
                .background(Color.Black),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            repeat(BOARD_DIM) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Log.v( "BoardView1", "BoardView called with board: $board")
                    Log.v( "BoardView2", "BoardView called with board: ${board.turn}")
                    repeat(BOARD_DIM) { col ->
                        val pos = Cell(row, col)
                        if(board.moves[pos] != null) {
                            CellView(player = board.moves[pos]) { onclick(pos) }

                        }
                        else {
                            CellView(
                                player = null,
                                onClick = { onclick(pos) },
                                )
                        }
                    }
                }
            }
        }
        if(board is BoardWin) ShowWinner(board)

    }
}

@Composable
private fun ShowWinner(board: BoardWin) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "winner!! : ${board.winner} ", color = Color.Green)
    }
}

@Composable
fun CellView(player: Player?,
             modifier: Modifier = Modifier
                 .size(cellSize)
                 .background(Color.LightGray),
             onClick: () -> Unit) {
    if (player == null) {
        Log.v( "CellView", "CellView called with player null : $player")
        Box(modifier = modifier.clickable(onClick = onClick)){
        }
    } else {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {

            DrawPiece(player, modifier = modifier)
        }
    }
}



@Composable
fun DrawPiece(player: Player?, modifier: Modifier ){
    Log.d("DrawPiece", "DrawPiece called for player: $player")
    if(player == Player.YELLOW ){
        Box (
            modifier = Modifier
                .fillMaxSize()
                .size(10.dp),
            contentAlignment = Alignment.Center,
            content = {
                Image(
                    painter = painterResource(id = R.drawable.yellow),
                    contentDescription = R.string.app_name.toString(),
                    modifier = modifier.size(10.dp))
            }
        )
    }
    else
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
            content = {
                Image(
                    painter = painterResource(id = R.drawable.red),
                    contentDescription = R.string.app_name.toString(),
                    modifier = modifier.size(10.dp))
            }
        )
}