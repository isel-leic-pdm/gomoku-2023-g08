package Gomoku.Rankings

import Gomoku.State.IdleUserRank
import Gomoku.State.LoadStateUserRank
import Gomoku.State.LoadedUser
import Gomoku.State.LoadedUserRank
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun RankingScreen(
    onBackRequested: () -> Unit = { },
    onFetchRankingRequest: () -> Unit = { },
    rank: LoadStateUserRank = IdleUserRank
) {
    val configuration = LocalConfiguration.current
    val isHorizontal = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isHorizontal) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f), // Consume remaining space
                verticalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    item {
                        Button(onClick = onBackRequested) {
                            Text("Back")
                        }
                    }

                    item {
                        if (rank is LoadedUserRank) {
                            Log.v("RANKING", "fetch:" + rank.toString())
                            RankView(rank = rank.result.getOrThrow())
                        }
                    }

                    item {
                        Button(onClick = onFetchRankingRequest) {
                            Text("Fetch Ranking")
                        }
                    }
                }
            )
        } else {
            // If not in landscape mode, show the regular items
            Button(
                onClick = onBackRequested,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text("Back")
            }

            if (rank is LoadedUserRank) {
                Log.v("RANKING", "fetch:" + rank.toString())
                RankView(rank = rank.result.getOrThrow())
            }

            Button(
                onClick = onFetchRankingRequest,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text("Fetch Ranking")
            }
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