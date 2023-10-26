package Gomoku.Games

import Gomoku.About.AboutActivity
import Gomoku.ErrorAlert
import Gomoku.State.Idle
import Gomoku.State.LoadState
import Gomoku.State.Loaded
import Gomoku.ui.BoardView
import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InitialScreen(
    activity: ComponentActivity,
    game: LoadState = Idle,
    onInfoRequested: () -> Unit = { },
    onFetch: () -> Unit = { }
) {
    val vm = remember { GameScreenViewModel() }
    Row {
        Text(text = "Gomoku",
            modifier = Modifier.padding(16.dp),
            fontSize = 30.sp)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround, // Organiza os botões verticalmente com espaço entre eles
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Button(
            modifier = Modifier
                .size(200.dp, 100.dp) // Define o tamanho do botão
                .padding(16.dp), // Adiciona um espaçamento ao redor do botão
            onClick = {
                UserActivity.navigateTo(activity)
            }
        ) {
            Text("Create User")
        }
        Button(
            modifier = Modifier
                .size(200.dp, 100.dp)
                .padding(16.dp),
            onClick = onInfoRequested
        ) {
            Text("New Game")
        }
        Button(
            modifier = Modifier
                .size(200.dp, 100.dp)
                .padding(16.dp),
            onClick = onInfoRequested
        ) {
            Text("Replay Game")
        }
        Button(
            modifier = Modifier
                .size(200.dp, 100.dp)
                .padding(16.dp),
            onClick = onInfoRequested
        ) {
            Text("Ranking")
        }
        Button(
            modifier = Modifier
                .size(200.dp, 100.dp)
                .padding(16.dp),
            onClick = {
                AboutActivity.navigateTo(activity)
            }
        ) {
            Text("About")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun appInitial() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        InitialScreen(activity = LocalContext.current as ComponentActivity)
            //ComponentActivity() )
    }
}



