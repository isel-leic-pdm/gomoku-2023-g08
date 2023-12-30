package Gomoku.Rankings

import Gomoku.State.IdleUserRank
import Gomoku.State.LoadStateUserRank
import Gomoku.State.LoadedUser
import Gomoku.State.LoadedUserRank
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(
    onBackRequested: () -> Unit = { },
    onFetchRankingRequest: () -> Unit = { },
    onUserSearchRequest: (String) -> Unit = { },
    rank: LoadStateUserRank = IdleUserRank
) {
    val nameInput = remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val isHorizontal = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val lazyColumnListState = rememberLazyListState()
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

                    item {
                        Text(
                            text = "Search by username",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    item {
                        TextField(
                            value = nameInput.value,
                            onValueChange = {
                                nameInput.value = it
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }

                    item {
                        Button(
                            onClick = {
                                onUserSearchRequest(nameInput.value)
                            },
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Search User")
                        }
                    }

                })
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = lazyColumnListState
                ) {
                    item {
                        RankView(rank = rank.result.getOrThrow())
                    }
                }

// Search UI
                TextField(
                    placeholder = { Text("Search by Username") },
                    value = nameInput.value,
                    onValueChange = {
                        nameInput.value = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

// Scroll automatically after the search
                //está a scrollar se ja tiver no 2º elemento
                LaunchedEffect(nameInput.value) {
                    val lowerCaseInput = nameInput.value.lowercase()
                    val userIndex =
                        rank.result.getOrThrow().indexOfFirst { it.username.lowercase() == lowerCaseInput  }
                    if (userIndex >=0) {
                        delay(500)
                        lazyColumnListState.animateScrollToItem(userIndex)
                    } else {
                        // If the user is not found, scroll to the top
                        delay(500)
                        lazyColumnListState.animateScrollToItem(0)
                    }
                }
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
                Text(" ")
                Text("${it.rank} - ${it.username}", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = if (it.rank == 1) Color.Yellow else if (it.rank==2) Color.Red else if (it.rank==3) Color.Gray else Color.Black)
                Text("Wins: ${it.wins}", fontSize = 20.sp)
                Text("Games: ${it.jogos}", fontSize = 20.sp)
                Text("")
            }
        }
    }
}