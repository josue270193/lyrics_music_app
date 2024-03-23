package app.josue.lyricsmusic.di

import android.content.Context
import com.google.android.horologist.audio.SystemAudioRepository
import com.google.android.horologist.media.ui.snackbar.SnackbarManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConfigModule {

//    @Singleton
//    @Provides
//    fun audioOutputSelector(
//        systemAudioRepository: SystemAudioRepository,
//    ): AudioOutputSelector =
//        BluetoothSettingsOutputSelector(systemAudioRepository)

    @Singleton
    @Provides
    fun systemAudioRepository(
        @ApplicationContext application: Context,
    ): SystemAudioRepository =
        SystemAudioRepository.fromContext(application)

    @Singleton
    @Provides
    fun snackbarManager() =
        SnackbarManager()

}