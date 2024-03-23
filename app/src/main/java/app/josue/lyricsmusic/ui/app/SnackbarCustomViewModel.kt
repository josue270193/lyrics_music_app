package app.josue.lyricsmusic.ui.app

import com.google.android.horologist.media.ui.snackbar.SnackbarManager
import com.google.android.horologist.media.ui.snackbar.SnackbarViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SnackbarCustomViewModel

@Inject
constructor(
    snackbarManager: SnackbarManager,
) : SnackbarViewModel(snackbarManager)