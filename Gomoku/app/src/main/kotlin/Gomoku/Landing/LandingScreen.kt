package Gomoku.Landing

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gomoku.R

@Composable
fun LandingScreen(
    onAheadRequested: () -> Unit = { }
) {
    val configuration = LocalConfiguration.current
    val isHorizontal = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE


    if (isHorizontal) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                item {
                    // Adiciona a imagem acima do botão
                    Image(
                        painter = painterResource(id = R.drawable.gomoku),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(top = 8.dp)
                    )
                }
                item {
                    // Adiciona o logo com menos espaço
                    Image(
                        painter = painterResource(id = R.drawable.gomoku_logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(500.dp)
                            .padding(top = 8.dp)
                    )
                }
                item {
                    Button(
                        onClick = onAheadRequested,
                        modifier = Modifier.padding(bottom = 20.dp)// Adiciona algum espaço entre a imagem e o botão
                    ) {
                        Text("Play")
                    }
                }
            }
        )
    } else {
        // Se o dispositivo estiver na orientação retrato, não aplicamos a rolagem
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Adiciona a imagem acima do botão
            Image(
                painter = painterResource(id = R.drawable.gomoku),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 8.dp)
            )

            // Adiciona o logo com menos espaço
            Image(
                painter = painterResource(id = R.drawable.gomoku_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(400.dp)
                    .padding(top = 8.dp)
            )

            Button(
                onClick = onAheadRequested,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text("Play")
            }
        }
    }
}
