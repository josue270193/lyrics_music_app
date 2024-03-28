package app.josue.lyricsmusic.ui.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class LyricsMusicApplication : Application() {

    @Inject
    lateinit var appConfig: AppConfig

}
