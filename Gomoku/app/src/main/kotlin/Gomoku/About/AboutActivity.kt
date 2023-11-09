package Gomoku.About

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gomoku.R


class AboutActivity : ComponentActivity() {

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, AboutActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "AboutActivity.onCreate() called")

        setContent {
            Log.v("ABOUTCREATED", "AboutActivity.onCreate() called")
            AboutScreen(
                onBackRequested = { finish() },
                onSendEmailRequested = { openSendEmail() },
                onOpenUrlRequested = { openURL(it) },
                socials = socialLinks,
            )
        }
    }

    private fun openURL(url: Uri) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url)
            startActivity(intent)
        }
        catch (e: ActivityNotFoundException) {
            Log.e(TAG, "Failed to open URL", e)
            Toast
                .makeText(
                    this,
                    R.string.activity_info_no_suitable_app,
                    Toast.LENGTH_LONG
                )
                .show()
        }
    }

    private fun openSendEmail() {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(AUTHOR_EMAIL))
                putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT)
            }

            startActivity(intent)
        }
        catch (e: ActivityNotFoundException) {
            Log.e(TAG, "Failed to send email", e)
            /*
            Toast
                .makeText(
                    this,
                    R.string.activity_info_no_suitable_app,
                    Toast.LENGTH_LONG
                )
                .show()

             */
        }
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG, "AboutActivity.onStart() called")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "AboutActivity.onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "AboutActivity.onDestroy() called")
    }
}

private val socialLinks = emptyList<SocialInfo>()


private const val AUTHOR_EMAIL = "paulo.pereira@isel.pt"
private const val EMAIL_SUBJECT = "About the Jokes App"
