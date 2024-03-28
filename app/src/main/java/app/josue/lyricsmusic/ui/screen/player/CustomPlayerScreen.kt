package app.josue.lyricsmusic.ui.screen.player

import androidx.activity.compose.ReportDrawnAfter
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.MaterialTheme
import app.josue.lyricsmusic.ui.app.VolumeCustomViewModel
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.media.ui.components.animated.AnimatedMediaControlButtons
import com.google.android.horologist.media.ui.components.animated.AnimatedMediaInfoDisplay
import com.google.android.horologist.media.ui.components.background.ArtworkColorBackground
import com.google.android.horologist.media.ui.screens.player.DefaultMediaInfoDisplay
import com.google.android.horologist.media.ui.screens.player.DefaultPlayerScreenControlButtons
import com.google.android.horologist.media.ui.screens.player.PlayerScreen
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun CustomPlayerScreen(
    mediaPlayerScreenViewModel: MediaPlayerScreenViewModel,
    volumeViewModel: VolumeCustomViewModel,
    onVolumeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val volumeUiState by volumeViewModel.volumeUiState.collectAsStateWithLifecycle()
    val settingsState by mediaPlayerScreenViewModel.settingsState.collectAsStateWithLifecycle()

    PlayerScreen(
        modifier = modifier,
        background = {
            val artworkUri = it.media?.artworkUri
            ArtworkColorBackground(
                artworkUri = artworkUri,
                defaultColor = MaterialTheme.colors.primary,
                modifier = Modifier.fillMaxSize(),
            )
        },
        playerViewModel = mediaPlayerScreenViewModel,
        volumeViewModel = volumeViewModel,
        mediaDisplay = { playerUiState ->
            if (settingsState.animated) {
                AnimatedMediaInfoDisplay(
                    media = playerUiState.media,
                    loading = !playerUiState.connected || playerUiState.media?.loading == true,
                )
            } else {
                DefaultMediaInfoDisplay(playerUiState)
            }
        },
        buttons = {
            CustomSettingsButtons(
                volumeUiState = volumeUiState,
                onVolumeClick = onVolumeClick,
                enabled = it.connected && it.media != null,
            )
        },
        controlButtons = { playerUiController, playerUiState ->
            if (settingsState.podcastControls) {
                PlayerScreenPodcastControlButtons(playerUiController, playerUiState)
            } else {
                if (settingsState.animated) {
                    AnimatedMediaControlButtons(
                        onPlayButtonClick = { playerUiController.play() },
                        onPauseButtonClick = { playerUiController.pause() },
                        playPauseButtonEnabled = playerUiState.playPauseEnabled,
                        playing = playerUiState.playing,
                        onSeekToPreviousButtonClick = { playerUiController.skipToPreviousMedia() },
                        onSeekToPreviousLongRepeatableClick = { playerUiController.seekBack() },
                        seekToPreviousButtonEnabled = playerUiState.seekToPreviousEnabled,
                        onSeekToNextButtonClick = { playerUiController.skipToNextMedia() },
                        onSeekToNextLongRepeatableClick = { playerUiController.seekForward() },
                        seekToNextButtonEnabled = playerUiState.seekToNextEnabled,
                        trackPositionUiModel = playerUiState.trackPositionUiModel,
                    )
                } else {
                    DefaultPlayerScreenControlButtons(playerUiController, playerUiState)
                }
            }
        },
    )

    ReportDrawnAfter {
        mediaPlayerScreenViewModel.playerState.filterNotNull().first()
    }

}

