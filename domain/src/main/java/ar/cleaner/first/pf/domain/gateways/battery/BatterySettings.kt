package ar.cleaner.first.pf.domain.gateways.battery

import ar.cleaner.first.pf.domain.models.BatteryMode

interface BatterySettings {

    fun saveTimeBatteryOptimization()

    fun isBatteryOptimized(): Boolean

    fun saveBatteryMode(mode: BatteryMode)

    fun getBatteryMode(): BatteryMode

}