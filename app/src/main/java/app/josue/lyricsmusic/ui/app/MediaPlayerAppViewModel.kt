package app.josue.lyricsmusic.ui.app

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MediaPlayerAppViewModel

@Inject
constructor() : ViewModel() {

    val deepLinkPrefix: String = ""

}
