package ar.cleaner.first.pf.ui.result

import ar.cleaner.first.pf.domain.models.CleanerDetails
import ar.cleaner.first.pf.domain.models.details.BatteryDetails
import ar.cleaner.first.pf.domain.models.details.BoostDetails
import ar.cleaner.first.pf.domain.models.details.TemperatureDetails
import ar.cleaner.first.pf.models.MenuHorizontalItems

data class ResultState(
    val itemsList:List<MenuHorizontalItems> = emptyList(),
    val boostDetails: BoostDetails? = null,
    val batteryDetails: BatteryDetails? = null,
    val temperatureDetails: TemperatureDetails? = null,
    val cleanerDetails: CleanerDetails? = null
)