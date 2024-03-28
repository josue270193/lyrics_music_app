package app.josue.lyricsmusic.ui.app

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavHostState
import app.josue.lyricsmusic.R
import app.josue.lyricsmusic.ui.navigation.Samples
import app.josue.lyricsmusic.ui.screen.lyrics.SamplesScreen
import app.josue.lyricsmusic.ui.screen.lyrics.SamplesScreenViewModel
import app.josue.lyricsmusic.ui.screen.player.CustomPlayerScreen
import app.josue.lyricsmusic.ui.theme.LyricsMusicAppTheme
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.audio.ui.components.actions.SettingsButton
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberColumnState
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.media.ui.navigation.MediaNavController.navigateToSettings
import com.google.android.horologist.media.ui.navigation.MediaNavController.navigateToVolume
import com.google.android.horologist.media.ui.navigation.MediaPlayerScaffold

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun WearApp(
    navController: NavHostController,
    intent: Intent,
    mediaPlayerViewModel: MediaPlayerAppViewModel = hiltViewModel(),
    volumeViewModel: VolumeCustomViewModel = hiltViewModel(),
    samplesScreenViewModel: SamplesScreenViewModel = hiltViewModel()
) {
    val navHostState = rememberSwipeDismissableNavHostState()

    val appState by mediaPlayerViewModel.appState.collectAsStateWithLifecycle()

    LyricsMusicAppTheme {
        MediaPlayerScaffold(
            playerScreen = {
                CustomPlayerScreen(
                    modifier = Modifier.fillMaxSize(),
                    mediaPlayerScreenViewModel = hiltViewModel(),
                    volumeViewModel = volumeViewModel,
                    onVolumeClick = {
                        navController.navigateToVolume()
                    },
                )
            },
            libraryScreen = {
                val columnState = rememberResponsiveColumnState()

                ScreenScaffold(scrollState = columnState) {
                    ScalingLazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        columnState = columnState
                    ) {
                        items(100) {
                            Text("i = $it")
                        }
                        item {
                            SettingsButton(
                                onClick = {
                                    navController.navigateToSettings()
                                },
                                imageVector = ImageVector.vectorResource(R.drawable.baseline_settings_24),
                                contentDescription = stringResource(id = R.string.settings)
                            )
                        }
                    }
                }
            },
            categoryEntityScreen = { _, name ->
                val columnState = rememberColumnState()

                ScreenScaffold(scrollState = columnState) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Entity Screen")
                    }
                }
            },
            mediaEntityScreen = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Media XXX")
                }
            },
            playlistsScreen = {
                val columnState = rememberColumnState()

                ScreenScaffold(scrollState = columnState) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Playlists Screen")
                    }
                }
            },
            settingsScreen = {
                val columnState = rememberColumnState()

                ScreenScaffold(scrollState = columnState) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Settings Screen")
                    }
                }
            },
            navHostState = navHostState,
            snackbarViewModel = hiltViewModel<SnackbarCustomViewModel>(),
            volumeViewModel = volumeViewModel,
            deepLinkPrefix = mediaPlayerViewModel.deepLinkPrefix,
            navController = navController,
            additionalNavRoutes = {
                composable(
                    route = Samples.navRoute,
                    arguments = Samples.arguments,
                    deepLinks = Samples.deepLinks(mediaPlayerViewModel.deepLinkPrefix),
                ) {
                    val columnState = rememberColumnState()

                    ScreenScaffold(scrollState = columnState) {
                        SamplesScreen(
                            columnState = columnState,
                            samplesScreenViewModel = samplesScreenViewModel,
                            navController = navController,
                        )
                    }
                }
            }
        )
    }
}
