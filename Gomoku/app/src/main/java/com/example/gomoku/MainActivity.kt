package com.example.gomoku

import Gomoku.ProjectAuthors.DisplayAuthors
import Gomoku.model.CreateGame
import Gomoku.model.ViewModel
import Gomoku.ui.BoardView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.tds.gomoku.model.BOARD_DIM
import pt.isel.tds.gomoku.model.Cell

import pt.isel.tds.gomoku.model.Player

val cellSize = 20.dp
val lineSize = 2.dp
val boardSize = cellSize * BOARD_DIM + lineSize*(BOARD_DIM -1)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           App()
        }
    }
    }
@Composable
fun App(){
    val vm = remember { ViewModel() }
    BoardView(vm, vm.game?.board, onclick = vm::play)
    DisplayAuthors()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun app() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        App()
    }

}



