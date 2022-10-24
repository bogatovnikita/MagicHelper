package ar.cleaner.first.pf.data.preferences.delegate

import androidx.datastore.preferences.core.edit
import ar.cleaner.first.pf.data.extensions.createKey
import ar.cleaner.first.pf.data.preferences.PreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PreferencesDelegate<T> @Inject constructor(
    private val defaultValue: T,
    private val customKey: String? = null
) {

    operator fun provideDelegate(
        thisRef: PreferencesManager,
        prop: KProperty<*>
    ): ReadWriteProperty<PreferencesManager, T> {

        val key = createKey(customKey ?: prop.name, defaultValue)

        return object : ReadWriteProperty<PreferencesManager, T> {

            private var storedValue: T? = null

            override fun getValue(thisRef: PreferencesManager, property: KProperty<*>): T {
                if (storedValue == null) {
                    val value = thisRef.store.data.map { pref ->
                        pref[key] ?: defaultValue
                    }
                    storedValue = runBlocking(Dispatchers.IO) {
                        value.first()
                    }
                }

                return storedValue!!
            }

            override fun setValue(thisRef: PreferencesManager, property: KProperty<*>, value: T) {
                storedValue = value

                with(thisRef) {
                    scope.launch {
                        store.edit { pref ->
                            pref[key] = value
                        }
                    }
                }
            }
        }
    }
}