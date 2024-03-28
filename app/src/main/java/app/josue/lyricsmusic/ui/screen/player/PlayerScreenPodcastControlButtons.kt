package app.josue.lyricsmusic.ui.screen.player

import androidx.compose.runtime.Composable
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.media.ui.components.PodcastControlButtons
import com.google.android.horologist.media.ui.state.PlayerUiController
import com.google.android.horologist.media.ui.state.PlayerUiState

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun PlayerScreenPodcastControlButtons(
    playerUiController: PlayerUiController,
    playerUiState: PlayerUiState,
) {
    PodcastControlButtons(
        playerController = playerUiController,
        playerUiState = playerUiState,
    )
}