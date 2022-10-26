package ar.cleaner.first.pf.ui.menu

import ar.cleaner.first.pf.domain.models.CleanerDetails
import ar.cleaner.first.pf.domain.models.details.BatteryDetails
import ar.cleaner.first.pf.domain.models.details.CpuDetails
import ar.cleaner.first.pf.domain.models.details.RamDetails
import ar.cleaner.first.pf.models.MenuItems

data class MenuState(
    val menuItemList: MutableMap<String, MenuItems> = mutableMapOf(),
    val ramDetails: RamDetails? = null,
    val batteryDetails: BatteryDetails? = null,
    val cpuDetails: CpuDetails? = null,
    val cleanerDetails: CleanerDetails? = null
)