package ar.cleaner.first.pf.data.settings.temperature

import android.app.Application
import ar.cleaner.first.pf.domain.repositorys.temperature.TemperatureSettings
import com.bogatovnikita.settings.FunctionSettings
import javax.inject.Inject

class TemperatureSettingImpl @Inject constructor(
    private val context: Application,
): TemperatureSettings {

    private val settings = FunctionSettings(context)

    override fun saveTimeTemperatureOptimization() = settings.saveTimeTemperatureOptimization()

    override fun isTemperatureChecked(): Boolean =settings.isTemperatureChecked()

    override fun saveLastTemperature(degree: Int) = settings.saveLastTemperature(degree)

    override fun gatLastTemperature(): Int = settings.gatLastTemperature()

}