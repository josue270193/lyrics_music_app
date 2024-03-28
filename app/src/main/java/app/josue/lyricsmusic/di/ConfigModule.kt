package app.josue.lyricsmusic.di

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.StrictMode
import app.josue.lyricsmusic.ui.app.AppConfig
import app.josue.lyricsmusic.ui.util.resetAfter
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.audio.SystemAudioRepository
import com.google.android.horologist.media3.audio.AudioOutputSelector
import com.google.android.horologist.media3.audio.BluetoothSettingsOutputSelector
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@OptIn(ExperimentalHorologistApi::class)
@Module
@InstallIn(SingletonComponent::class)
object ConfigModule  {
    @Singleton
    @Provides
    @IsEmulator
    fun isEmulator() = listOf(Build.PRODUCT, Build.MODEL).any { it.startsWith("sdk_gwear") }

    @Singleton
    @Provides
    @SuppressSpeakerPlayback
    fun suppressSpeakerPlayback(
        @IsEmulator isEmulator: Boolean,
    ) = !isEmulator

    @Singleton
    @Provides
    fun appConfig(): AppConfig = AppConfig()

    @Singleton
    @Provides
    @CacheDir
    fun cacheDir(
        @ApplicationContext application: Context,
    ): File =
        StrictMode.allowThreadDiskWrites().resetAfter {
            application.cacheDir
        }

    @Singleton
    @Provides
    fun audioOutputSelector(
        systemAudioRepository: SystemAudioRepository,
    ): AudioOutputSelector =
        BluetoothSettingsOutputSelector(systemAudioRepository)

    @Singleton
    @Provides
    fun systemAudioRepository(
        @ApplicationContext application: Context,
    ): SystemAudioRepository =
        SystemAudioRepository.fromContext(application)

    @Singleton
    @Provides
    fun notificationManager(
        @ApplicationContext application: Context,
    ): NotificationManager =
        application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}