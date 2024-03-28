package app.josue.lyricsmusic.di

import android.annotation.SuppressLint
import androidx.media3.exoplayer.ExoPlayer.AudioOffloadListener

@SuppressLint("UnsafeOptInUsageError")
class AudioOffloadListenerList : AudioOffloadListener {
    private val audioOffloadListeners = mutableListOf<AudioOffloadListener>()

    fun addListener(audioOffloadListener: AudioOffloadListener) {
        synchronized(audioOffloadListeners) {
            audioOffloadListeners.add(audioOffloadListener)
        }
    }

    fun removeListener(audioOffloadListener: AudioOffloadListener) {
        synchronized(audioOffloadListeners) {
            audioOffloadListeners.remove(audioOffloadListener)
        }
    }

    override fun onSleepingForOffloadChanged(isSleepingForOffload: Boolean) {
        synchronized(audioOffloadListeners) {
            for (it in audioOffloadListeners) {
                it.onSleepingForOffloadChanged(isSleepingForOffload)
            }
        }
    }

    override fun onOffloadedPlayback(isOffloadedPlayback: Boolean) {
        synchronized(audioOffloadListeners) {
            for (it in audioOffloadListeners) {
                it.onOffloadedPlayback(isOffloadedPlayback)
            }
        }
    }
}