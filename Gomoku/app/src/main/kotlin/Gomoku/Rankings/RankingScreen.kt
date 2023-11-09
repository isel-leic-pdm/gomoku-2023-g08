package Gomoku.Rankings

import Gomoku.State.IdleUserRank
import Gomoku.State.LoadStateUserRank
import Gomoku.State.LoadedUser
import Gomoku.State.LoadedUserRank
import android.util.Log
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
        Button(onClick = onBackRequested) {
                Text("Back")
            }

        Button(onClick = onFetchRankingRequest) {
            Text("Fetch Ranking")

            }
        if(rank is LoadedUserRank){
            Log.v("RANKING", "fetch:" +rank.toString())
            RankView(rank = rank.result.getOrThrow())

        }

        }
    }
@Composable
fun RankView(rank: List<UsersRankOutput>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            rank.forEach {
                Text("Username: ${it.username}")
                Text("Wins: ${it.wins}")
                Text("Rank: ${it.rank}")
                Text("Jogos: ${it.jogos}")
            }
        }
    }
}
