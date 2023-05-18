package ar.cleaner.first.pf.domain.usecases.temperature

import ar.cleaner.first.pf.domain.repositorys.temperature.TemperatureProvider
import ar.cleaner.first.pf.domain.repositorys.temperature.TemperatureSettings
import javax.inject.Inject

class GetCpuDetailsUseCase @Inject constructor(
    private val settings: TemperatureSettings,
    private val temperature: TemperatureProvider,
) {

    fun isOptimized() = settings.isTemperatureChecked()

    fun getTemperature(): Int {
        return if (settings.isTemperatureChecked()) {
            temperature.getTemperature()
        } else {
            settings.gatLastTemperature()
        }
    }

}