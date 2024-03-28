package app.josue.lyricsmusic.ui.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.josue.lyricsmusic.domain.SettingsRepository
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.media.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MediaPlayerAppViewModel

@OptIn(ExperimentalHorologistApi::class)
@Inject
constructor(
    appConfig: AppConfig,
    private val settingsRepository: SettingsRepository,
    private val playerRepository: PlayerRepository,
//    private val authUserRepository: AuthUserRepository,
) : ViewModel() {

    val deepLinkPrefix: String = appConfig.deeplinkUriPrefix

    val appState = settingsRepository.settingsFlow.map {
        MediaPlayerAppState(
            streamingMode = it.streamingMode,
            guestMode = it.guestMode,
        )
    }.stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = MediaPlayerAppState())

    suspend fun shouldShowLoginPrompt(): Boolean {
        return !isGuestMode() && !isLoggedIn()
    }

    suspend fun isGuestMode(): Boolean {
        return appState.filter { it.guestMode != null }.first().guestMode == true
    }

    suspend fun isLoggedIn(): Boolean {
//        return authUserRepository.getAuthenticated() != null
        return false
    }
}

data class MediaPlayerAppState(
    val streamingMode: Boolean? = null,
    val guestMode: Boolean? = null,
)
