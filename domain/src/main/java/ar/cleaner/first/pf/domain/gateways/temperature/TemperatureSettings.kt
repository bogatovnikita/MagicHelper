package ar.cleaner.first.pf.domain.gateways.temperature

interface TemperatureSettings {

    fun saveTimeTemperatureOptimization()

    fun isTemperatureChecked(): Boolean

    fun saveLastTemperature(degree: Int)

    fun gatLastTemperature(): Int

}