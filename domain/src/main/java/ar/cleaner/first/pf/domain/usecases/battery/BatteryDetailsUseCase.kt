package ar.cleaner.first.pf.domain.usecases.battery

import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.models.details.BatteryDetails
import ar.cleaner.first.pf.domain.gateways.battery.BatteryProvider
import ar.cleaner.first.pf.domain.utils.isTimePassed
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class BatteryDetailsUseCase @Inject constructor(
    private val batteryProvider: BatteryProvider,
    private val dispatcher: CoroutineDispatcher
) {

    fun getBatteryDetails(): Flow<BatteryDetails> =
        batteryProvider.getBatteryPercents().map { percents ->
//            val isOptimized = !isTimePassed(batteryProvider.lastOptimizationMillis.first())
//            val mode = batteryProvider.getBatteryMode().map { mode ->
//                when (mode) {
//                    BatteryMode.HIGH.name -> BatteryMode.HIGH
//                    BatteryMode.MEDIUM.name -> BatteryMode.MEDIUM
//                    BatteryMode.NORMAL.name -> BatteryMode.NORMAL
//                    else -> throw IllegalArgumentException() TODO Settings
//                }
//            }.first()
            BatteryDetails(
                batteryCharge = percents,
                batteryMode = BatteryMode.HIGH,
                isOptimized = false
            )
        }
            .catch { e ->
                e.printStackTrace()
            }
            .flowOn(dispatcher)

}