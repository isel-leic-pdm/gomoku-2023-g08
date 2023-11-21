package Gomoku.Main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    onCreateUserReq: () -> Unit = { },
    onLoginReq: () -> Unit = { },
    onAboutreq: () -> Unit = { }
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
            Text(
                text = "Gomoku",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(100.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

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
                onClick = onLoginReq
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
