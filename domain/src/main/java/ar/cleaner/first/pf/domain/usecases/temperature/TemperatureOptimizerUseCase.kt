package ar.cleaner.first.pf.domain.usecases.temperature

import ar.cleaner.first.pf.domain.repositorys.temperature.TemperatureProvider
import ar.cleaner.first.pf.domain.repositorys.temperature.TemperatureSettings
import javax.inject.Inject

class TemperatureOptimizerUseCase @Inject constructor(
    private val settings: TemperatureSettings,
    private val temperature: TemperatureProvider,
) {
    fun saveTimeTemperatureOptimization() {
        settings.saveTimeTemperatureOptimization()
        settings.saveLastTemperature(temperature.getTemperature())
    }

}