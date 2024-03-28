package app.josue.lyricsmusic.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IsEmulator

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SuppressSpeakerPlayback

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CacheDir

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ForApplicationScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ForServiceScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ForViewModelScope