package app.josue.lyricsmusic.ui.app

import android.os.Build
import app.josue.lyricsmusic.BuildConfig
import java.io.File

data class AppConfig (
    val offloadEnabled: Boolean = Build.VERSION.SDK_INT >= 30,
    val deeplinkUriPrefix: String = "uamp${if (BuildConfig.DEBUG) "-debug" else ""}://uamp",
    val cacheDir: File? = null,
)
