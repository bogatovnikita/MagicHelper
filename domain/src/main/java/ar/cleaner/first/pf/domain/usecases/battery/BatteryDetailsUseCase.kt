package ar.cleaner.first.pf.domain.usecases.battery

import ar.cleaner.first.pf.domain.models.details.BatteryDetails
import ar.cleaner.first.pf.domain.gateways.battery.BatteryProvider
import ar.cleaner.first.pf.domain.gateways.battery.BatterySettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class BatteryDetailsUseCase @Inject constructor(
    private val settings: BatterySettings,
    private val dispatcher: CoroutineDispatcher,
    private val batteryProvider: BatteryProvider,
) {

    fun getBatteryDetails(): Flow<BatteryDetails> =
        batteryProvider.getBatteryPercents().combine(batteryProvider.getTimeToFullCharge()) { percents, time ->
            BatteryDetails(
                batteryCharge = percents,
                batteryMode = settings.getBatteryMode(),
                isOptimized = settings.isBatteryOptimized(),
                timeToFullCharge = time,
                isCharging = batteryProvider.isCharging()
            )
        }.flowOn(dispatcher)

}