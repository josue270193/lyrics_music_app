package app.josue.lyricsmusic.ui.screen.lyrics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.rememberActiveFocusRequester
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumnState
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberColumnState
import com.google.android.horologist.compose.rotaryinput.rotaryWithScroll

@OptIn(ExperimentalHorologistApi::class, ExperimentalWearFoundationApi::class)
@Composable
fun SamplesScreen(
    columnState: ScalingLazyColumnState,
    samplesScreenViewModel: SamplesScreenViewModel = viewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val uiState by samplesScreenViewModel.uiState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()
    ScreenScaffold(scrollState = scrollState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .rotaryWithScroll(scrollState, rememberActiveFocusRequester())
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = uiState.lyrics)
        }
    }
}

@OptIn(ExperimentalHorologistApi::class)
@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun SamplesScreenPreview() {
    val columnState = rememberColumnState()
    val navController = rememberSwipeDismissableNavController()

    SamplesScreen(columnState = columnState, navController = navController)
}