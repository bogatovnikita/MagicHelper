package ar.cleaner.first.pf.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import ar.cleaner.first.pf.data.extensions.stateValuePref
import ar.cleaner.first.pf.data.preferences.delegate.PreferencesDelegate
import ar.cleaner.first.pf.domain.const.Const.DEFAULT_MILLIS
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

    var batteryMode: String by PreferencesDelegate(BatteryMode.NORMAL.name)
    val batteryModeFlow = store.stateValuePref(
        prop = this::batteryMode,
        defaultValue = BatteryMode.NORMAL.name,
        scope = scope
    )

}