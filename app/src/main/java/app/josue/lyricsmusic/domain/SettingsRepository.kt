package app.josue.lyricsmusic.domain

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow

class SettingsRepository(
    private val dataStore: DataStore<SettingsDto>,
) {

    suspend fun edit(transform: suspend (SettingsDto) -> SettingsDto) {
        dataStore.updateData {
            transform(it)
        }
    }

    val settingsFlow: Flow<SettingsDto> = dataStore.data
}