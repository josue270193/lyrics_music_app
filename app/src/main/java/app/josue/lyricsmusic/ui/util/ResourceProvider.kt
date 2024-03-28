package app.josue.lyricsmusic.ui.util

import android.content.res.Resources
import androidx.annotation.StringRes

class ResourceProvider(
    private val resources: Resources,
) {
    fun getString(
        @StringRes id: Int,
    ): String = resources.getString(id)
}