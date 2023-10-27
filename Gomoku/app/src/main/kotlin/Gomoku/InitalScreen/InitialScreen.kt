package Gomoku.InitalScreen

import android.os.Build
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
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InitialScreen(
    onCreateUserReq: () -> Unit = { },
    onLoginReq: () -> Unit = { },
    onCreateGameReq: () -> Unit = { },
    onReplayGameReq: () -> Unit = { },
    onRankingReq: () -> Unit = { },
    onAboutreq: () -> Unit = { }
) {

    Row {
        Text(text = "Gomoku", modifier = Modifier.padding(16.dp),)
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
            onClick = onCreateUserReq
        ) {
            Text("Create User")
        }
        Button(
            modifier = Modifier
                .size(200.dp, 100.dp) // Define o tamanho do botão
                .padding(16.dp), // Adiciona um espaçamento ao redor do botão
            onClick = onLoginReq
        ) {
            Text("Login User")
        }
        Button(
            modifier = Modifier
                .size(200.dp, 100.dp)
                .padding(16.dp),
            onClick = onCreateGameReq
        ) {
            Text("New Game")
        }
        Button(
            modifier = Modifier
                .size(200.dp, 100.dp)
                .padding(16.dp),
            onClick = onReplayGameReq
        ) {
            Text("Replay Game")
        }
        Button(
            modifier = Modifier
                .size(200.dp, 100.dp)
                .padding(16.dp),
            onClick = onRankingReq
        ) {
            Text("Ranking")
        }
        Button(
            modifier = Modifier
                .size(200.dp, 100.dp)
                .padding(16.dp),
            onClick = onAboutreq
        ) {
            Text("About")
        }
    }
}
/*
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true,
    device = "spec:width=1080px,height=2340px,dpi=440"
)
@Composable
fun appInitial() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        InitialScreen()
    }


}
 */