package Gomoku.ITEMS

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
private fun GomokuTheme() {
    com.example.gomoku.ui.theme.GomokuTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            // BoardView(vm, vm.board) { pos ->
            //      vm.play(pos)
            //     }


        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GomokuTheme {
        BoardView(vm, vm.board) { pos ->
                vm.play(pos)



        }
    }
}


fun Board.getPiece(pos: Cell): Player {
    return when(this){
        is BoardRun -> moves[pos]!!
        else -> error("Game is over")
    }
}
@Composable
fun Insert( player: Player) =
   DrawPiece(player =player, modifier = Modifier.size(cellSize) )









 */