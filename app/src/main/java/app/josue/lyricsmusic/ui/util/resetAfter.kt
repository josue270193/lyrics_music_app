package app.josue.lyricsmusic.ui.util

import android.os.StrictMode

// Confusingly the result of allowThreadDiskWrites is the old policy,
// while allow* methods immediately apply the change.
// So `this` is the policy before we overrode it.
fun <R> StrictMode.ThreadPolicy.resetAfter(block: () -> R) = try {
    block()
} finally {
    StrictMode.setThreadPolicy(this)
}