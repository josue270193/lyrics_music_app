package app.josue.lyricsmusic.di

import androidx.datastore.core.DataStore
import app.josue.lyricsmusic.domain.SettingsDto
import app.josue.lyricsmusic.domain.SettingsRepository
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.media.data.mapper.MediaExtrasMapper
import com.google.android.horologist.media.data.mapper.MediaExtrasMapperNoopImpl
import com.google.android.horologist.media.data.mapper.MediaMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@OptIn(ExperimentalHorologistApi::class)
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun mediaExtrasMapper(): MediaExtrasMapper = MediaExtrasMapperNoopImpl

    @Provides
    fun mediaMapper(mediaExtrasMapper: MediaExtrasMapper): MediaMapper = MediaMapper(mediaExtrasMapper)

    @Singleton
    @Provides
    fun settingsRepository(
        prefsDataStore: DataStore<SettingsDto>,
    ) =
        SettingsRepository(prefsDataStore)

}