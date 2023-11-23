package isel.pdm.demos.tictactoe.ui.preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
/*
class UserPreferencesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserPreferencesScreen(
                userInfo = null,
                onSaveRequested = { TODO() },
                onNavigateBackRequested = { TODO() }
            )
        }
    }
}


 */

object DataStoreKeys {
    val USER_ID = stringPreferencesKey("user_id")
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
}
