package app.josue.lyricsmusic.domain

import java.io.InputStream
import java.io.OutputStream

class SettingsDto (
    val streamingMode: Boolean = false,
    val guestMode: Boolean = false,
    val animated: Boolean = false,
    val podcastControls: Boolean = false
) {
    fun writeTo(output: OutputStream) {
        TODO("Not yet implemented")
    }


    companion object {
        fun getDefaultInstance(): SettingsDto {
            return SettingsDto()
        }

        fun parseFrom(input: InputStream): SettingsDto {
            return SettingsDto()
        }
    }
}
