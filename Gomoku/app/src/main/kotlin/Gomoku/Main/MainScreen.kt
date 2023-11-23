package Gomoku.Main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gomoku.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    onCreateUserReq: () -> Unit = { },
    onLoginReq: () -> Unit = { },
    onAboutreq: () -> Unit = { },
    onLoggedEnabled: Boolean = true,
    onplayrequested: () -> Unit = { }
) {
    MaterialTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
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
                        .size(350.dp)
                        .padding(top = 8.dp)
                )


            Button(
                modifier = Modifier
                    .size(200.dp, 100.dp)
                    .padding(16.dp),
                onClick = onCreateUserReq
            ) {
                Text("Create User")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .size(200.dp, 100.dp)
                    .padding(16.dp),
                 enabled = onLoggedEnabled,
                onClick = {
                    onLoginReq()
                    onplayrequested()
                }
            ) {
                Text("Login User")
            }

            Spacer(modifier = Modifier.height(16.dp))

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
}
