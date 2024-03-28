package app.josue.lyricsmusic.ui.screen.lyrics

import androidx.lifecycle.ViewModel
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.media.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SamplesScreenViewModel

@OptIn(ExperimentalHorologistApi::class)
@Inject
constructor(
    private val playerRepository: PlayerRepository,
) : ViewModel() {

    val uiState: StateFlow<UiState> = MutableStateFlow(
        UiState(
            lyrics = ""
        ),
    )

    data class UiState(
        val lyrics: String,
    )

}