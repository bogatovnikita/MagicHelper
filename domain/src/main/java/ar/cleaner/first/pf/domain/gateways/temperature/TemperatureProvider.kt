package ar.cleaner.first.pf.domain.gateways.temperature

interface TemperatureProvider {

    fun getTemperature(): Int

}