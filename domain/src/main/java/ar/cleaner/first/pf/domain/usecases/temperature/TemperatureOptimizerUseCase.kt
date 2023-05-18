package ar.cleaner.first.pf.domain.usecases.temperature

import ar.cleaner.first.pf.domain.repositorys.temperature.TemperatureSettings
import javax.inject.Inject

class TemperatureOptimizerUseCase @Inject constructor(
    private val settings: TemperatureSettings
) {
    fun saveTimeTemperatureOptimization(): Unit = settings.saveTimeTemperatureOptimization()

}