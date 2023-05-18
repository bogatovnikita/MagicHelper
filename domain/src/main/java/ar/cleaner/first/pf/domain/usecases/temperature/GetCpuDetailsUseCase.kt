package ar.cleaner.first.pf.domain.usecases.temperature

import ar.cleaner.first.pf.domain.repositorys.temperature.TemperatureSettings
import javax.inject.Inject

class GetCpuDetailsUseCase @Inject constructor(
    private val settings: TemperatureSettings,
) {

    fun isOptimized() = settings.isTemperatureChecked()

    fun getTemperature(): Int = settings.gatLastTemperature()

}