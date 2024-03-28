package app.josue.lyricsmusic.ui.screen.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import app.josue.lyricsmusic.R
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.audio.ui.components.actions.SettingsButton

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
) {
    var faved by remember { mutableStateOf(false) }

    SettingsButton(
        modifier = modifier,
        onClick = { faved = !faved },
        imageVector = if (faved) ImageVector.vectorResource(R.drawable.baseline_celebration_24) else
            ImageVector.vectorResource(R.drawable.baseline_settings_24),
        contentDescription = stringResource(R.string.lyrics),
    )
}