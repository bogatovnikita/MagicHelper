package ar.cleaner.first.pf.ui.battery

import android.app.Application
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.domain.models.BatteryMode
import javax.inject.Inject

class BatteryModeFunctionList @Inject constructor(
    private val context: Application,
) {

    private val actions = context.resources.getStringArray(R.array.battery_items).toList()

    fun getListMode(mode: BatteryMode) = when(mode) {
        BatteryMode.NORMAL -> actions.subList(0, 2)
        BatteryMode.MEDIUM -> actions.subList(0, 4)
        BatteryMode.HIGH -> actions
    }

}