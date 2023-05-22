package ar.cleaner.first.pf.domain.usecases.temperature

import ar.cleaner.first.pf.domain.gateways.temperature.TemperatureSettings
import javax.inject.Inject

class TemperatureUseCase @Inject constructor(
    private val settings: TemperatureSettings,
) {

    fun isOptimized() = settings.isTemperatureChecked()

    fun getTemperature(): Int = settings.getLastTemperature()

}