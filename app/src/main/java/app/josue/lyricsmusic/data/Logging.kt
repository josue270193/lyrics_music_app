package app.josue.lyricsmusic.data

import android.content.res.Resources
import android.util.Log
import androidx.annotation.StringRes
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.media3.logging.ErrorReporter

@OptIn(ExperimentalHorologistApi::class)
class Logging(
    private val res: Resources,
) : ErrorReporter {
    override fun showMessage(
        @StringRes message: Int,
    ) {
        val messageString = res.getString(message)
        Log.i("ErrorReporter", messageString)
    }

    private val ErrorReporter.Level.loggingLevel: Int
        get() = when (this) {
            ErrorReporter.Level.Error -> Log.ERROR
            ErrorReporter.Level.Info -> Log.INFO
            ErrorReporter.Level.Debug -> Log.DEBUG
        }

    override fun logMessage(message: String, category: ErrorReporter.Category, level: ErrorReporter.Level) {
        Log.println(level.loggingLevel, category.name, message)
    }
}