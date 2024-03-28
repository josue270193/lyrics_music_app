package app.josue.lyricsmusic.di

import android.annotation.SuppressLint
import app.josue.lyricsmusic.ui.app.AppConfig
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.media3.logging.ErrorReporter
import com.google.android.horologist.media3.service.NetworkAwareDownloadListener
import com.google.android.horologist.networks.highbandwidth.HighBandwidthNetworkMediator
import com.google.android.horologist.networks.logging.NetworkStatusLogger
import com.google.android.horologist.networks.rules.NetworkingRulesEngine
import com.google.android.horologist.networks.status.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@OptIn(ExperimentalHorologistApi::class)
@SuppressLint("UnsafeOptInUsageError")
@Module
@InstallIn(SingletonComponent::class)
object DownloadModule {

    private const val DOWNLOAD_WORK_MANAGER_SCHEDULER_WORK_NAME = "mediasample_download"

//    @DownloadFeature
//    @Singleton
//    @Provides
//    fun downloadDataSourceFactory(
//        callFactory: Call.Factory,
//        @DownloadFeature transferListener: TransferListener,
//    ): DataSource.Factory = OkHttpDataSource.Factory(
//        NetworkAwareCallFactory(
//            delegate = callFactory,
//            defaultRequestType = DownloadRequest,
//        ),
//    )
//        .setCacheControl(CacheControl.Builder().noCache().noStore().build())
//        .setTransferListener(transferListener)
//
//    @DownloadFeature
//    @Provides
//    fun transferListener(errorReporter: ErrorReporter): TransferListener =
//        TransferListener(errorReporter)
//
//    @DownloadFeature
//    @Singleton
//    @Provides
//    fun threadPool(): ExecutorService = Executors.newCachedThreadPool()
//
//    @Singleton
//    @Provides
//    fun downloadNotificationHelper(
//        @ApplicationContext applicationContext: Context,
//    ): DownloadNotificationHelper =
//        DownloadNotificationHelper(
//            applicationContext,
//            MediaDownloadServiceImpl.MEDIA_DOWNLOAD_CHANNEL_ID,
//        )
//
//    @DownloadFeature
//    @Singleton
//    @Provides
//    fun databaseProvider(
//        @ApplicationContext application: Context,
//    ): DatabaseProvider = StandaloneDatabaseProvider(application)
//
//    @Singleton
//    @Provides
//    fun downloadManager(
//        @ApplicationContext applicationContext: Context,
//        @DownloadFeature databaseProvider: DatabaseProvider,
//        downloadCache: Cache,
//        @DownloadFeature dataSourceFactory: DataSource.Factory,
//        @DownloadFeature threadPool: ExecutorService,
//        downloadManagerListener: DownloadManagerListener,
//        appConfig: AppConfig,
//        networkAwareListener: Provider<NetworkAwareDownloadListener>,
//    ) = DownloadManager(
//        applicationContext,
//        databaseProvider,
//        downloadCache,
//        dataSourceFactory,
//        threadPool,
//    ).also {
//        it.addListener(downloadManagerListener)
////        if (appConfig.strictNetworking != null) {
//            it.addListener(networkAwareListener.get())
////        }
//    }
//
//    @Provides
//    fun downloadIndex(downloadManager: DownloadManager): DownloadIndex = downloadManager.downloadIndex
//
//    @Singleton
//    @Provides
//    fun workManagerScheduler(
//        @ApplicationContext applicationContext: Context,
//    ) = WorkManagerScheduler(applicationContext, DOWNLOAD_WORK_MANAGER_SCHEDULER_WORK_NAME)
//
//    @DownloadFeature
//    @Provides
//    @Singleton
//    fun coroutineScope(
//        @Dispatcher(IO) ioDispatcher: CoroutineDispatcher,
//    ): CoroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher)
//
//    @Provides
//    @Singleton
//    fun downloadManagerListener(
//        @DownloadFeature coroutineScope: CoroutineScope,
//        mediaDownloadLocalDataSource: MediaDownloadLocalDataSource,
//        downloadProgressMonitor: DownloadProgressMonitor,
//    ): DownloadManagerListener = DownloadManagerListener(
//        coroutineScope = coroutineScope,
//        mediaDownloadLocalDataSource = mediaDownloadLocalDataSource,
//        downloadProgressMonitor = downloadProgressMonitor,
//    )
//
//    @Provides
//    @Singleton
//    fun downloadProgressMonitor(
//        @DownloadFeature coroutineScope: CoroutineScope,
//        mediaDownloadLocalDataSource: MediaDownloadLocalDataSource,
//    ): DownloadProgressMonitor = DownloadProgressMonitor(
//        coroutineScope = coroutineScope,
//        mediaDownloadLocalDataSource = mediaDownloadLocalDataSource,
//    )

    @Provides
    fun networkingRulesEngine(
        networkRepository: NetworkRepository,
        networkLogger: NetworkStatusLogger,
        appConfig: AppConfig,
    ): NetworkingRulesEngine = NetworkingRulesEngine(
        networkRepository = networkRepository,
        logger = networkLogger,
//        networkingRules = appConfig.strictNetworking!!,
    )

    @Provides
    @Singleton
    fun networkAwareListener(
        errorReporter: ErrorReporter,
        highBandwithRequester: HighBandwidthNetworkMediator,
        networkingRulesEngine: NetworkingRulesEngine,
    ): NetworkAwareDownloadListener = NetworkAwareDownloadListener(
        errorReporter,
        highBandwithRequester,
        networkingRulesEngine,
    )
}