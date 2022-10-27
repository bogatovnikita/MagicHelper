package ar.cleaner.first.pf.ui.result

import ar.cleaner.first.pf.domain.models.CleanerDetails
import ar.cleaner.first.pf.domain.models.details.BatteryDetails
import ar.cleaner.first.pf.domain.models.details.CpuDetails
import ar.cleaner.first.pf.domain.models.details.RamDetails
import ar.cleaner.first.pf.models.MenuHorizontalItems

data class ResultState(
    val itemsList:List<MenuHorizontalItems> = emptyList(),
    val ramDetails: RamDetails? = null,
    val batteryDetails: BatteryDetails? = null,
    val cpuDetails: CpuDetails? = null,
    val cleanerDetails: CleanerDetails? = null
)