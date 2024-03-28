package app.josue.lyricsmusic.ui.screen.player

import androidx.lifecycle.viewModelScope
import app.josue.lyricsmusic.domain.SettingsDto
import app.josue.lyricsmusic.domain.SettingsRepository
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.media.data.repository.PlayerRepositoryImpl
import com.google.android.horologist.media.ui.state.PlayerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalHorologistApi::class)
@HiltViewModel
class MediaPlayerScreenViewModel

@Inject
constructor(
    playerRepository: PlayerRepositoryImpl,
    settingsRepository: SettingsRepository,
) : PlayerViewModel(playerRepository) {

    init {
        viewModelScope.launch {
            playerRepository.currentMedia.collect { media ->
                if (media != null) {
//                    settingsRepository.edit {
//                        it.copy { currentMediaItemId = media.id }
//                    }
                }
            }
        }
    }

    val playerState = playerRepository.player

    val settingsState: StateFlow<SettingsDto> = settingsRepository.settingsFlow
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
            initialValue = SettingsDto.getDefaultInstance(),
        )
}
