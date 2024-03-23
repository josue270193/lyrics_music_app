package app.josue.lyricsmusic.ui.screen

import androidx.lifecycle.ViewModel
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.media.model.Media
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SamplesScreenViewModel

@Inject
constructor() : ViewModel() {

    val uiState: StateFlow<UiState> = MutableStateFlow(
        UiState(
            samples = listOf(),
        ),
    )

    @OptIn(ExperimentalHorologistApi::class)
    data class Sample(
        val id: Int,
        val name: String,
        val mediaItems: List<Media>,
    )

    data class UiState(
        val samples: List<Sample>,
    )

}