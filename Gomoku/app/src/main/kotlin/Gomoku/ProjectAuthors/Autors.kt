package Gomoku.ProjectAuthors

import Gomoku.ui.BoardView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

import isel.pdm.jokes.ui.theme.AuthorsTheme


fun getAuthors():String {
    return "Ricardo Oliveira a:49436 \n Goncalo Pinto a:49476"
}
const val AuthorTestTag = "AuthorTestTag"
const val FetchButtonTestTag = "FetchButtonTestTag"

@Composable
fun DisplayAuthors() {
    val authors  = remember { mutableStateOf("") }

    AuthorsTheme {
        Surface(

            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,

                ) {
                if(authors.value.isNotBlank()){
                    Text(text = authors.value, modifier = Modifier.testTag(AuthorTestTag))
                }
                Button(onClick = {
                    if(authors.value== ""){
                        authors.value = getAuthors()
                    }
                    else{
                        authors.value = ""
                    }
                }, modifier = Modifier.testTag(FetchButtonTestTag)) {
                    Text(text = "Credits")
                }

            }

        }

    }

}


