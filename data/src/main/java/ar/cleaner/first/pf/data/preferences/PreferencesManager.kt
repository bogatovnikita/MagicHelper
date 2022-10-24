package ar.cleaner.first.pf.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import ar.cleaner.first.pf.data.extensions.stateValuePref
import ar.cleaner.first.pf.data.preferences.delegate.PreferencesDelegate
import ar.cleaner.first.pf.domain.const.Const.BOOL_DEFAULT_VALUE
import ar.cleaner.first.pf.domain.const.Const.DEFAULT_DOUBLE_VALUE
import ar.cleaner.first.pf.domain.const.Const.DEFAULT_MILLIS
import ar.cleaner.first.pf.domain.const.Const.DEFAULT_URI
import ar.cleaner.first.pf.domain.models.BatteryMode
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class PreferencesManager @Inject constructor(
    val scope: CoroutineScope,
    val store: DataStore<Preferences>
) {

    var batteryLastOptimizationMillis by PreferencesDelegate(DEFAULT_MILLIS)
    val batteryLastOptimizationFlow = store.stateValuePref(
        prop = this::batteryLastOptimizationMillis,
        defaultValue = DEFAULT_MILLIS,
        scope = scope
    )

    var cpuLastOptimizationMillis by PreferencesDelegate(DEFAULT_MILLIS)
    val cpuLastOptimizationFlow = store.stateValuePref(
        prop = this::cpuLastOptimizationMillis,
        defaultValue = DEFAULT_MILLIS,
        scope = scope
    )

    var cleanerLastOptimizationMillis by PreferencesDelegate(DEFAULT_MILLIS)
    val cleanerOptimizationFlow = store.stateValuePref(
        prop = this::cleanerLastOptimizationMillis,
        defaultValue = DEFAULT_MILLIS,
        scope = scope
    )

    var ramLastOptimizationMillis by PreferencesDelegate(DEFAULT_MILLIS)
    val ramOptimizationFlow = store.stateValuePref(
        prop = this::ramLastOptimizationMillis,
        defaultValue = DEFAULT_MILLIS,
        scope = scope
    )

    var lastClearedJunk: Double by PreferencesDelegate(DEFAULT_DOUBLE_VALUE)
    val lastClearedJunkFlow = store.stateValuePref(
        prop = this::lastClearedJunk,
        defaultValue = DEFAULT_DOUBLE_VALUE,
        scope = scope
    )

    var batteryMode: String by PreferencesDelegate(BatteryMode.NORMAL.name)
    val batteryModeFlow = store.stateValuePref(
        prop = this::batteryMode,
        defaultValue = BatteryMode.NORMAL.name,
        scope = scope
    )

    var hasOptimizeAll: Boolean by PreferencesDelegate(BOOL_DEFAULT_VALUE)
    val hasOptimizeAllFlow = store.stateValuePref(
        prop = this::hasOptimizeAll,
        defaultValue = BOOL_DEFAULT_VALUE,
        scope = scope
    )

    var dataFolderUri: String by PreferencesDelegate(DEFAULT_URI)
    val dataFolderUriFlow = store.stateValuePref(
        prop = this::dataFolderUri,
        defaultValue = DEFAULT_URI,
        scope = scope
    )
}