package ar.cleaner.first.pf.ui.menu

import ar.cleaner.first.pf.domain.models.CleanerDetails
import ar.cleaner.first.pf.domain.models.details.BatteryDetails
import ar.cleaner.first.pf.domain.models.details.TemperatureDetails
import ar.cleaner.first.pf.domain.models.details.RamDetails

data class MenuState(
    val ramDetails: RamDetails? = null,
    val batteryDetails: BatteryDetails? = null,
    val temperatureDetails: TemperatureDetails? = null,
    val cleanerDetails: CleanerDetails? = null
)