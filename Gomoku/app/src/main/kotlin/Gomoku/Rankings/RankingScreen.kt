package Gomoku.Rankings

import Gomoku.State.IdleUserRank
import Gomoku.State.LoadStateUserRank
import Gomoku.State.LoadedUser
import Gomoku.State.LoadedUserRank
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RankingScreen(
    onBackRequested: () -> Unit = { },
    onFetchRankingRequest: () -> Unit = { },
    rank: LoadStateUserRank = IdleUserRank){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(onClick = onBackRequested) {
                Text("Back")
            }
        }
        Text("Ranking")
        Button(onClick = onFetchRankingRequest) {
            Text("Fetch Ranking")
            if(rank is LoadedUserRank){
                rank.result.getOrThrow().forEach {
                    RankView(rank = it)
                }

            }
            /*
            if (rank is LoadedUserRank && rank.result.isFailure) {
                ErrorAlert(
                    title = R.string.error_api_title,
                    message = R.string.error_could_not_reach_api,
                    buttonText = R.string.error_retry_button_text,
                    onDismiss = onFetch
                )
            }

             */
        }
    }
}



@Composable
fun RankView(rank: UsersRankOutput) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "User: ${rank.username}") // Substitua `username` pelo campo correto
            Text(text = "Ranking: ${rank.rank}") // Substitua `score` pelo campo correto
            Text(text = "Wins: ${rank.wins}") // Substitua `score` pelo campo correto
            Text(text = "Jogos feitos: ${rank.jogos}") // Substitua `score` pelo campo correto
            // Adicione mais informações do rank, se necessário
        }
    }
}
