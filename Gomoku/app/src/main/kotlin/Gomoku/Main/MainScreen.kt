package Gomoku.Main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gomoku.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    onCreateUserReq: () -> Unit = { },
    onLoginReq: () -> Unit = { },
    onAboutreq: () -> Unit = { },
    onLoggedEnabled: Boolean = true,
    onPlayRequested: () -> Unit = { }
) {
    MaterialTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Adiciona o logo com menos espaço
            Image(
                painter = painterResource(id = R.drawable.gomoku_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .testTag("gomoku_logo_tag")
            )

            Button(
                modifier = Modifier
                    .size(300.dp, 60.dp)
                    .padding(16.dp)
                    .shadow(4.dp, shape = CircleShape),
                onClick = onCreateUserReq
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Create User", fontSize = 10.sp)
                }
            }

            Button(
                modifier = Modifier
                    .size(250.dp, 60.dp)
                    .padding(16.dp)
                    .shadow(4.dp, shape = CircleShape),
                enabled = onLoggedEnabled,
                onClick = {
                    onLoginReq()
                    onPlayRequested()
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Login & Play", fontSize = 11.sp)
                }
            }

            Button(
                modifier = Modifier
                    .size(200.dp, 60.dp)
                    .padding(16.dp)
                    .shadow(4.dp, shape = CircleShape),
                onClick = onAboutreq
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("About", fontSize = 9.sp)
                }
            }
        }
    }
}
