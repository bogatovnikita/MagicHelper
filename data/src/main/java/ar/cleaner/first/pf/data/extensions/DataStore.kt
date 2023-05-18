package ar.cleaner.first.pf.data.extensions

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.reflect.KProperty

fun <T> DataStore<Preferences>.stateValuePref(
    prop: KProperty<*>,
    defaultValue: T,
    scope: CoroutineScope
): StateFlow<T> =
    this.data.map { pref ->
        pref[createKey(prop.name, defaultValue)] ?: defaultValue
    }.stateIn(scope, started = SharingStarted.Eagerly, defaultValue)

fun <T> createKey(name: String, value: T): Preferences.Key<T> =
    when (value) {
        is Int -> intPreferencesKey(name)
        is Long -> longPreferencesKey(name)
        is Double -> doublePreferencesKey(name)
        is Float -> floatPreferencesKey(name)
        is String -> stringSetPreferencesKey(name)
        is Boolean -> booleanPreferencesKey(name)
        else -> throw IllegalArgumentException()
    }.run { this as Preferences.Key<T> }
