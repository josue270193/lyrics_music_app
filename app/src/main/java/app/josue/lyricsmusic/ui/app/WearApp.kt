package app.josue.lyricsmusic.ui.app

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import androidx.wear.compose.navigation.rememberSwipeDismissableNavHostState
import app.josue.lyricsmusic.R
import app.josue.lyricsmusic.ui.navigation.Samples
import app.josue.lyricsmusic.ui.screen.SamplesScreen
import app.josue.lyricsmusic.ui.theme.LyricsMusicAppTheme
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.audio.ui.components.actions.SettingsButton
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberColumnState
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.media.ui.components.MediaControlButtons
import com.google.android.horologist.media.ui.components.display.TextMediaDisplay
import com.google.android.horologist.media.ui.navigation.MediaNavController.navigateToSettings
import com.google.android.horologist.media.ui.navigation.MediaPlayerScaffold
import com.google.android.horologist.media.ui.screens.player.PlayerScreen
import com.google.android.horologist.media.ui.snackbar.SnackbarViewModel

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun WearApp(
    navController: NavHostController,
    intent: Intent,
) {
    val appViewModel: MediaPlayerAppViewModel = hiltViewModel()
    val volumeViewModel: VolumeCustomViewModel = hiltViewModel()
    val navHostState = rememberSwipeDismissableNavHostState()

    LyricsMusicAppTheme {
        MediaPlayerScaffold(
            playerScreen = @Composable {
                PlayerScreen(
                    mediaDisplay = {
                        TextMediaDisplay(
                            title = "Song name",
                            subtitle = "Artist name"
                        )
                    },
                    controlButtons = {
                        MediaControlButtons(
                            onPlayButtonClick = { },
                            onPauseButtonClick = { },
                            playPauseButtonEnabled = true,
                            playing = false,
                            onSeekToPreviousButtonClick = { },
                            seekToPreviousButtonEnabled = true,
                            onSeekToNextButtonClick = { },
                            seekToNextButtonEnabled = true
                        )
                    },
                    buttons = {
                        SettingsButton(
                            onClick = {
                                      navController.navigateToSettings()
                            },
                            imageVector = ImageVector.vectorResource(R.drawable.baseline_settings_24),
                            contentDescription = stringResource(id = R.string.settings)
                        )
                        SettingsButton(
                            onClick = {
                                navController.navigate(Samples.destination())
                            },
                            imageVector = ImageVector.vectorResource(R.drawable.baseline_celebration_24),
                            contentDescription = stringResource(id = R.string.lyrics)
                        )
                    }
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
//            timeText = {
//                MediaInfoTimeText(
//                    mediaInfoTimeTextViewModel = mediaInfoTimeTextViewModel,
//                )
//            },
            volumeViewModel = volumeViewModel,
            deepLinkPrefix = appViewModel.deepLinkPrefix,
            navController = navController,
            additionalNavRoutes = {
                composable(
                    route = Samples.navRoute,

                    arguments = Samples.arguments,
                    deepLinks = Samples.deepLinks(appViewModel.deepLinkPrefix),
                ) {
                    val columnState = rememberColumnState()

                    ScreenScaffold(scrollState = columnState) {
                        SamplesScreen(
                            columnState = columnState,
                            samplesScreenViewModel = hiltViewModel(),
                            navController = navController,
                        )
                    }
                }
            }
        )
    }

}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    val navController = rememberSwipeDismissableNavController()

    WearApp(navController = navController,
        intent = Intent()
    )
}