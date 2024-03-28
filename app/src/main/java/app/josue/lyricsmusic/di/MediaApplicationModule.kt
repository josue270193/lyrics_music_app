package app.josue.lyricsmusic.di

import android.annotation.SuppressLint
import android.content.Context
import android.os.Vibrator
import androidx.datastore.core.DataStore
import androidx.media3.database.DatabaseProvider
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import app.josue.lyricsmusic.data.Logging
import app.josue.lyricsmusic.data.setting.settingsStore
import app.josue.lyricsmusic.domain.SettingsDto
import app.josue.lyricsmusic.ui.app.AppConfig
import app.josue.lyricsmusic.ui.util.ResourceProvider
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.media.ui.snackbar.SnackbarManager
import com.google.android.horologist.media3.config.WearMedia3Factory
import com.google.android.horologist.media3.logging.ErrorReporter
import com.google.android.horologist.media3.navigation.IntentBuilder
import com.google.android.horologist.media3.navigation.NavDeepLinkIntentBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.io.File
import javax.inject.Singleton

@OptIn(ExperimentalHorologistApi::class)
@SuppressLint("UnsafeOptInUsageError")
@Module
@InstallIn(SingletonComponent::class)
object MediaApplicationModule  {

    @Singleton
    @Provides
    fun intentBuilder(
        @ApplicationContext application: Context,
        appConfig: AppConfig,
    ): IntentBuilder =
        NavDeepLinkIntentBuilder(
            application,
            "${appConfig.deeplinkUriPrefix}/player?page=1",
            "${appConfig.deeplinkUriPrefix}/player?page=0",
        )

    @Singleton
    @Provides
    fun prefsDataStore(
        @ApplicationContext application: Context,
    ): DataStore<SettingsDto> =
        application.settingsStore

    @Singleton
    @Provides
    @ForApplicationScope
    fun coroutineScope(): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Singleton
    @Provides
    fun audioOffloadListener(
        listeners: AudioOffloadListenerList,
    ): ExoPlayer.AudioOffloadListener = listeners

    @Singleton
    @Provides
    fun audioOffloadListenerList(): AudioOffloadListenerList = AudioOffloadListenerList()

    @Singleton
    @Provides
    fun wearMedia3Factory(
        @ApplicationContext application: Context,
    ): WearMedia3Factory =
        WearMedia3Factory(application)

//    @Singleton
//    @Provides
//    fun audioOffloadManager(
//        logger: ErrorReporter,
//        settingsRepository: SettingsRepository,
//        @ForApplicationScope coroutineScope: CoroutineScope,
//        appConfig: AppConfig,
//        audioOffloadListenerList: AudioOffloadListenerList,
//    ): AudioOffloadManager {
//        return AudioOffloadManager(
//            logger,
//        ).also { audioOffloadManager ->
//            if (appConfig.offloadEnabled && Build.VERSION.SDK_INT >= 30) {
//                audioOffloadListenerList.addListener(audioOffloadManager.audioOffloadListener)
//
//                coroutineScope.launch {
//                    settingsRepository.settingsFlow.map { it.debugOffload }
//                        .collectLatest { debug ->
//                            if (debug) {
//                                audioOffloadManager.printDebugLogsLoop()
//                            }
//                        }
//                }
//            }
//        }
//    }

    @Singleton
    @Provides
    fun logger(
        @ApplicationContext application: Context,
    ): Logging = Logging(res = application.resources)

    @Singleton
    @Provides
    fun errorReporter(
        logging: Logging,
    ): ErrorReporter = logging

    @Singleton
    @Provides
    fun vibrator(
        @ApplicationContext application: Context,
    ): Vibrator =
        application.getSystemService(Vibrator::class.java)

    @Singleton
    @Provides
    fun cacheDatabaseProvider(
        @ApplicationContext application: Context,
    ): DatabaseProvider = StandaloneDatabaseProvider(application)

    @Singleton
    @Provides
    fun media3Cache(
        @CacheDir cacheDir: File,
        cacheDatabaseProvider: DatabaseProvider,
    ): Cache =
        SimpleCache(
            cacheDir.resolve("media3cache"),
            NoOpCacheEvictor(),
            cacheDatabaseProvider,
        )

    @Singleton
    @Provides
    fun snackbarManager() =
        SnackbarManager()

    @Singleton
    @Provides
    fun ResourceProvider(
        @ApplicationContext application: Context,
    ): ResourceProvider = ResourceProvider(application.resources)

//    @Singleton
//    @Provides
//    fun dataUpdates(
//        @ApplicationContext application: Context,
//    ): DataUpdates {
//        val updater = ComplicationDataSourceUpdateRequester.create(
//            application,
//            ComponentName(
//                application,
//                MediaStatusComplicationService::class.java,
//            ),
//        )
//        return DataUpdates(updater)
//    }
}