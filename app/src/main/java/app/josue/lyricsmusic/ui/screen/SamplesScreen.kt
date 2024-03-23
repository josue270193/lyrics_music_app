package app.josue.lyricsmusic.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.foundation.lazy.items
import app.josue.lyricsmusic.R
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnState

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun SamplesScreen(
    columnState: ScalingLazyColumnState,
    samplesScreenViewModel: SamplesScreenViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val uiState by samplesScreenViewModel.uiState.collectAsStateWithLifecycle()

    ScalingLazyColumn(
        columnState = columnState,
        modifier = modifier,
    ) {
        item {
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier.padding(bottom = 12.dp),
                style = MaterialTheme.typography.title3,
            )
        }
        items(uiState.samples) {
//            ActionSetting(text = it.name) {
//                samplesScreenViewModel.playSamples(it.id)
//                navController.navigateToPlayer()
//            }
        }
    }
}