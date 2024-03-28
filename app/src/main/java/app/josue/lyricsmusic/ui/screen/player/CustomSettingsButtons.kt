package app.josue.lyricsmusic.ui.screen.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.josue.lyricsmusic.R
import com.google.android.horologist.audio.ui.VolumeUiState
import com.google.android.horologist.audio.ui.components.SettingsButtonsDefaults
import com.google.android.horologist.audio.ui.components.actions.SetVolumeButton

@Composable
fun CustomSettingsButtons(
    volumeUiState: VolumeUiState,
    onVolumeClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier.fillMaxWidth(0.8124f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        FavoriteButton()

        SettingsButtonsDefaults.BrandIcon(
            iconId = R.drawable.baseline_settings_24,
            enabled = enabled,
        )

        SetVolumeButton(
            onVolumeClick = onVolumeClick,
            volumeUiState = volumeUiState,
        )
    }
}