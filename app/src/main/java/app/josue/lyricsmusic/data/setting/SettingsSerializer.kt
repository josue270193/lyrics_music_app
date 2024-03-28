package app.josue.lyricsmusic.data.setting

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import app.josue.lyricsmusic.domain.SettingsDto
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer : Serializer<SettingsDto> {
    override val defaultValue: SettingsDto
        get() = SettingsDto.getDefaultInstance()

//    override val defaultValue: SettingsDto = settings {
//        this.animated = true
//        this.debugOffload = false
//        this.loadItemsAtStartup = false
//        this.podcastControls = false
//        this.showTimeTextInfo = false
//        this.currentPosition = 0
//    }

    override suspend fun readFrom(input: InputStream): SettingsDto {
        try {
            return SettingsDto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: SettingsDto, output: OutputStream) = t.writeTo(output)
}

val Context.settingsStore: DataStore<SettingsDto> by dataStore(
    fileName = "settings.pb",
    serializer = SettingsSerializer,
)