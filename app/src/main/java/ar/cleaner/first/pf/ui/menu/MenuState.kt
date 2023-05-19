package ar.cleaner.first.pf.ui.menu

import ar.cleaner.first.pf.domain.models.CleanerDetails
import ar.cleaner.first.pf.domain.models.details.BatteryDetails
import ar.cleaner.first.pf.domain.models.details.BoostDetails
import ar.cleaner.first.pf.domain.models.details.TemperatureDetails

data class MenuState(
    val boostDetails: BoostDetails? = null,
    val batteryDetails: BatteryDetails? = null,
    val temperatureDetails: TemperatureDetails? = null,
    val cleanerDetails: CleanerDetails? = null
)