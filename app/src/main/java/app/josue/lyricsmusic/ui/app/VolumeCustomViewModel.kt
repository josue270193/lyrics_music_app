package app.josue.lyricsmusic.ui.app

import android.os.Vibrator
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.audio.SystemAudioRepository
import com.google.android.horologist.audio.ui.VolumeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@OptIn(ExperimentalHorologistApi::class)
@HiltViewModel
class VolumeCustomViewModel

@Inject
constructor(
    systemAudioRepository: SystemAudioRepository,
    vibrator: Vibrator
) : VolumeViewModel(
    volumeRepository = systemAudioRepository,
    audioOutputRepository = systemAudioRepository,
    vibrator = vibrator,
)
