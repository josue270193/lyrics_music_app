package app.josue.lyricsmusic.di

import android.annotation.SuppressLint
import android.app.Service
import android.os.Build
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.common.TrackSelectionParameters.AudioOffloadPreferences
import androidx.media3.common.util.Clock
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.okhttp.OkHttpDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.LoadControl
import androidx.media3.exoplayer.RenderersFactory
import androidx.media3.exoplayer.analytics.AnalyticsCollector
import androidx.media3.exoplayer.analytics.DefaultAnalyticsCollector
import androidx.media3.exoplayer.audio.DefaultAudioSink
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.extractor.DefaultExtractorsFactory
import androidx.media3.extractor.ExtractorsFactory
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaLibraryService.MediaLibrarySession
import app.josue.lyricsmusic.data.service.CustomMediaLibrarySessionCallback
import app.josue.lyricsmusic.ui.app.AppConfig
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.media3.config.WearMedia3Factory
import com.google.android.horologist.media3.logging.AnalyticsEventLogger
import com.google.android.horologist.media3.logging.ErrorReporter
import com.google.android.horologist.media3.logging.TransferListener
import com.google.android.horologist.media3.navigation.IntentBuilder
import com.google.android.horologist.media3.tracing.TracingListener
import com.google.android.horologist.networks.data.RequestType.MediaRequest.Companion.StreamRequest
import com.google.android.horologist.networks.okhttp.NetworkAwareCallFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.CacheControl
import okhttp3.Call

@OptIn(ExperimentalHorologistApi::class)
@SuppressLint("UnsafeOptInUsageError")
@Module
@InstallIn(ServiceComponent::class)
object PlaybackServiceModule {
    @ServiceScoped
    @Provides
    fun loadControl(): LoadControl = DefaultLoadControl.Builder()
        .setBackBuffer(
            /* backBufferDurationMs = */
            30_000,
            /* retainBackBufferFromKeyframe = */
            false,
        )
        .build()

    @ServiceScoped
    @Provides
    fun mediaCodecSelector(
        wearMedia3Factory: WearMedia3Factory,
    ): MediaCodecSelector = wearMedia3Factory.mediaCodecSelector()

    @ServiceScoped
    @Provides
    fun audioOnlyRenderersFactory(
        wearMedia3Factory: WearMedia3Factory,
        audioSink: DefaultAudioSink,
        mediaCodecSelector: MediaCodecSelector,
    ) =
        wearMedia3Factory.audioOnlyRenderersFactory(
            audioSink,
            mediaCodecSelector,
        )

    @ServiceScoped
    @Provides
    fun defaultAnalyticsCollector(
        logger: ErrorReporter,
    ): AnalyticsCollector =
        DefaultAnalyticsCollector(Clock.DEFAULT).apply {
            addListener(AnalyticsEventLogger(logger))
        }

    @ServiceScoped
    @Provides
    fun extractorsFactory(): ExtractorsFactory =
        DefaultExtractorsFactory()

    @ServiceScoped
    @Provides
    fun transferListener(
        logger: ErrorReporter,
    ) = TransferListener(logger)

    @ServiceScoped
    @Provides
    fun streamDataSourceFactory(
        callFactory: Call.Factory,
        transferListener: TransferListener,
    ): OkHttpDataSource.Factory =
        OkHttpDataSource.Factory(
            NetworkAwareCallFactory(
                callFactory,
                defaultRequestType = StreamRequest,
            ),
        )
            .setCacheControl(CacheControl.Builder().noCache().noStore().build())
            .setTransferListener(transferListener)

    @ServiceScoped
    @Provides
    fun cacheDataSourceFactory(
        downloadCache: Cache,
        streamDataSourceFactory: OkHttpDataSource.Factory,
        transferListener: TransferListener,
        appConfig: AppConfig,
    ): CacheDataSource.Factory =
        CacheDataSource.Factory()
            .setCache(downloadCache)
            .setUpstreamDataSourceFactory(streamDataSourceFactory)
            .setEventListener(transferListener)
            .apply {
//                if (!appConfig.cacheWriteBack) {
//                    setCacheWriteDataSinkFactory(null)
//                }
            }

    @ServiceScoped
    @Provides
    fun mediaSourceFactory(
        appConfig: AppConfig,
        cacheDataSourceFactory: CacheDataSource.Factory,
        streamDataSourceFactory: OkHttpDataSource.Factory,
        extractorsFactory: ExtractorsFactory,
    ): MediaSource.Factory {
        val dataSourceFactory =
//            if (appConfig.cacheItems) {
//                cacheDataSourceFactory
//            } else {
                streamDataSourceFactory
//            }
        return DefaultMediaSourceFactory(dataSourceFactory, extractorsFactory)
    }

    @ServiceScoped
    @Provides
    fun exoPlayer(
        service: Service,
        loadControl: LoadControl,
        audioOnlyRenderersFactory: RenderersFactory,
        analyticsCollector: AnalyticsCollector,
        mediaSourceFactory: MediaSource.Factory,
//        dataUpdates: DataUpdates,
//        audioOffloadManager: Provider<AudioOffloadManager>,
        appConfig: AppConfig,
        serviceCoroutineScope: CoroutineScope,
//        @SuppressSpeakerPlayback suppressSpeakerPlayback: Boolean,
    ): Player =
        ExoPlayer.Builder(service, audioOnlyRenderersFactory)
            .setAnalyticsCollector(analyticsCollector)
            .setMediaSourceFactory(mediaSourceFactory)
            .setAudioAttributes(AudioAttributes.DEFAULT, true)
//            .setSuppressPlaybackOnUnsuitableOutput(suppressSpeakerPlayback)
            .setWakeMode(C.WAKE_MODE_NETWORK)
            .setLoadControl(loadControl)
            .setSeekForwardIncrementMs(10_000)
            .setSeekBackIncrementMs(10_000)
            .build().apply {
                addListener(analyticsCollector)
//                addListener(dataUpdates.listener)
//                addListener(WearUnsuitableOutputPlaybackSuppressionResolverListener(service))
                addListener(TracingListener())

                trackSelectionParameters = trackSelectionParameters.buildUpon()
                    .setAudioOffloadPreferences(
                        AudioOffloadPreferences.Builder()
                            .setAudioOffloadMode(AudioOffloadPreferences.AUDIO_OFFLOAD_MODE_ENABLED)
                            .setIsSpeedChangeSupportRequired(false)
                            .setIsGaplessSupportRequired(false)
                            .build(),
                    )
                    .build()

                if (appConfig.offloadEnabled && Build.VERSION.SDK_INT >= 30) {
                    serviceCoroutineScope.launch {
//                        audioOffloadManager.get().connect(this@apply)
                    }
                }
            }

    @ServiceScoped
    @Provides
    fun serviceCoroutineScope(
        service: Service,
    ): CoroutineScope {
        return (service as LifecycleOwner).lifecycleScope
    }

    @ServiceScoped
    @Provides
    fun librarySessionCallback(
        logger: ErrorReporter,
        serviceCoroutineScope: CoroutineScope,
    ): MediaLibrarySession.Callback =
        CustomMediaLibrarySessionCallback(serviceCoroutineScope, logger)

    @ServiceScoped
    @Provides
    fun mediaLibrarySession(
        service: Service,
        player: Player,
        librarySessionCallback: MediaLibrarySession.Callback,
        intentBuilder: IntentBuilder,
    ): MediaLibrarySession =
        MediaLibrarySession.Builder(
            service as MediaLibraryService,
            player,
            librarySessionCallback,
        )
            .setSessionActivity(intentBuilder.buildPlayerIntent())
            .build().also {
                (service as LifecycleOwner).lifecycle.addObserver(
                    object :
                        DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            it.release()
                        }
                    },
                )
            }

    @ServiceScoped
    @Provides
    fun audioSink(
        wearMedia3Factory: WearMedia3Factory,
        audioOffloadListener: ExoPlayer.AudioOffloadListener,
        service: Service,
    ): DefaultAudioSink {
        return wearMedia3Factory.audioSink(
            audioOffloadListener = audioOffloadListener,
        ).also { audioSink ->
            if (service is LifecycleOwner) {
                service.lifecycle.addObserver(
                    object : DefaultLifecycleObserver {
                        override fun onStop(owner: LifecycleOwner) {
                            audioSink.reset()
                        }
                    },
                )
            }
        }
    }
}