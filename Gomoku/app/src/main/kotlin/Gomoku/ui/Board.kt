package Gomoku.ui

import Gomoku.DomainModel.Board
import Gomoku.DomainModel.Cell
import Gomoku.DomainModel.Player
import Gomoku.AfterLogin.cellSize
import Gomoku.AfterLogin.lineSize
import Gomoku.DomainModel.BOARD_DIM

import Gomoku.DomainModel.variantes
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gomoku.R

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


@Composable
fun BoardView(board: Board, onclick: (Int, Int) -> Unit, variante : variantes?) {
    val gameMoves = board.moves
    Log.v("BoardView", "Board size  : ${board.moves.size}")
    val dim = if(board.moves.size == 225)15 else 19
    val boardSize = cellSize * dim + lineSize *(dim -1)
    Log.v("BoardView", "BoardView called with board  : $board")
    Log.v("BoardView", "BoardView called with board  : ${board.moves}")
        Column(
            modifier = Modifier
                .size(boardSize)
                .background(Color.Black)
               .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            repeat(dim) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    repeat(dim) { col ->
                        val pos = Cell(row, col)
                        val player = board.moves[pos]
                      //  Log.v("MOVESKEYS", "${board.moves.keys}")
                     //   Log.v("MOVESVALUES", "${board.moves.values}")
                        Log.v("COMPARE", "${gameMoves.keys.contains(pos)}")
                        Log.v("POS", "pos : $pos, player =${gameMoves[pos]}")

                        CellView(player = gameMoves[pos] ?: Player.EMPTY) { onclick(pos.rowIndex, pos.colIndex) }
                    }
                }
            }
        }
    }


/*
@Composable
fun BoardView(board: Board, onclick: (Cell) -> Unit, variante : variantes?) {
    val moves = board.moves
    Log.v("BoardView", "Board size  : ${board.moves.size}")
    val dim = if(board.moves.size == 225)15 else 19
    val boardSize = cellSize * dim + lineSize *(dim -1)
    Log.v("BoardView", "BoardView called with board  : $board")
    Column(
        modifier = Modifier
            .size(boardSize)
            .background(Color.Black)
            .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
      for (cell in moves){
          val player = cell.value
            val pos = cell.key
          if(player == Player.EMPTY){
              CellView(player = null, onClick = { onclick(pos) })
          }
          else
              CellView(player = player, onClick = { onclick(pos) })
      }

    }
}


 */
/*
@Preview
@Composable
fun BoardViewPreview() {
    BoardView(Board(Allmoves(), null, null), onclick = {}, variante = variantes.OMOK)
}

 */
fun Allmoves(): Map<Cell, Player> {
    BOARD_DIM = 15
    val moves = mutableMapOf<Cell, Player>()
    for (row in 1 ..  15) {
        for (col in 1 .. 15) {
           moves.keys.add(Cell(row, col))
            moves.values.add(Player.EMPTY)
        }
    }
    Log.v("BoardView", "BoardView called with board  moves in allmoves : $moves")
    return moves
}



@Composable
fun CellView(player: Player?, modifier: Modifier = Modifier.size(cellSize).background(Color.LightGray), onClick: () -> Unit) {
    Log.v("CellView_PLAYER", "CellView called with player : $player")
    if (player == Player.EMPTY) {
        Box(modifier = modifier.clickable(onClick = onClick)){
        }
    } else {
       // Log.v( "CellView", "CellView called with player not null : $player")
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
                    painter = painterResource(id = R.drawable.red),
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
                    painter = painterResource(id = R.drawable.yellow),
                    contentDescription = R.string.app_name.toString(),
                    modifier = modifier.size(10.dp))
            }
        )
}
