package Gomoku.ui

import Gomoku.DomainModel.BOARD_DIM
import Gomoku.DomainModel.Board
import Gomoku.DomainModel.Cell
import Gomoku.DomainModel.Player
import Gomoku.DomainModel.moves
import Gomoku.Games.GameScreenViewModel
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
import Gomoku.Games.boardSize
import Gomoku.Games.cellSize
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BoardView(vm: GameScreenViewModel, board: Board?, onclick: (Cell) -> Unit,  ) {
    if(board == null){
        Column(
            modifier = Modifier
                .size(boardSize)
                .background(Color.Black).border(5.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
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
                .background(Color.Black).border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            repeat(BOARD_DIM) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
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
        if(board.isWin(moves =board.moves )) ShowWinner(board)

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
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ShowWinner(board: Board) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(chooseColor(board.winner!!))
                .border(5.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Winner: ${board.winner}",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }
}
@Composable
fun chooseColor(winner: Player): Color {
   if(winner == Player.PLAYER_X)
       return Color.Red
    else
       return Color.Yellow
}



@Composable
fun DrawPiece(player: Player?, modifier: Modifier ){
    Log.d("DrawPiece", "DrawPiece called for player: $player")
    if(player == Player.PLAYER_O){
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