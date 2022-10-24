package ar.cleaner.first.pf.data.local_receiver

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.cancellable

val brightnessUri: Uri = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS)

fun Context.brightnessStateChangeFlow() = callbackFlow {
    val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            trySendBlocking(
                Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
            )
        }
    }

    contentResolver.registerContentObserver(
        brightnessUri, false,
        observer
    )

    awaitClose {
        contentResolver.unregisterContentObserver(observer)
    }
}.cancellable()
