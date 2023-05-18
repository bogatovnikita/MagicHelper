package ar.cleaner.first.pf.data.providers.temperature

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import ar.cleaner.first.pf.domain.repositorys.temperature.TemperatureProvider
import javax.inject.Inject

class TemperatureProviderImpl @Inject constructor(
    private val context: Application,
): TemperatureProvider {

    private val batteryStatusIntent: Intent?
        get() {
            val batFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            return context.registerReceiver(null, batFilter)
        }

    private val batteryTemperature: Float
        get() {
            val intent = batteryStatusIntent
            val temperature = intent!!.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)
            return (temperature / 10.0).toFloat()
        }


    override fun getTemperature(): Int = batteryTemperature.toInt()

}