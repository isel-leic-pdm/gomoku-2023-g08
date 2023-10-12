package Gomoku

import Gomoku.ProjectAuthors.DisplayAuthors
import Gomoku.model.ViewModel
import Gomoku.ui.BoardView
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun MainScreen(onInfoRequested: () -> Unit = { }) {
    val vm = remember { ViewModel() }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Button(
            modifier = Modifier.testTag("InfoButton" ),
            onClick = onInfoRequested
        ) {
             Text("InfoButton")
        }


        BoardView(vm, vm.game?.board, onclick = vm::play)
        DisplayAuthors()
    }
}




