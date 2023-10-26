package Gomoku.About
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UserScreen() {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Username")
        TextFieldExample()
        Text("Password")
        TextFieldExample()
        Text("Confirm Password")
        TextFieldExample()
        Button(onClick = { /* Handle submit button click */ }) {
            Text("Submit")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldExample() {
    val keyboardController = LocalSoftwareKeyboardController.current
    val density = LocalDensity.current.density
    val imeAction = remember { ImeAction.Done }

    BasicTextField(
        value = "",
        onValueChange = { /* Handle text field value change */ },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        }),
        singleLine = true,
        textStyle = TextStyle(color = Color.Black),
        modifier = Modifier
            .background(Color.White)
            .padding(8.dp)
            .height(48.dp)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserScreenPreview() {
    UserScreen()
}
