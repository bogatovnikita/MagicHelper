package ar.cleaner.first.pf.domain.usecases.battery

import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.models.details.BatteryDetails
import ar.cleaner.first.pf.domain.gateways.battery.BatteryUseCaseRepository
import ar.cleaner.first.pf.domain.utils.isTimePassed
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class BatteryDetailsUseCase @Inject constructor(
    private val batteryUseCaseRepository: BatteryUseCaseRepository,
    private val dispatcher: CoroutineDispatcher
) {

    fun getBatteryDetails(): Flow<BatteryDetails> =
        batteryUseCaseRepository.getBatteryPercents().map { percents ->
            val isOptimized = !isTimePassed(batteryUseCaseRepository.lastOptimizationMillis.first())
            val mode = batteryUseCaseRepository.getBatteryMode().map { mode ->
                when (mode) {
                    BatteryMode.HIGH.name -> BatteryMode.HIGH
                    BatteryMode.MEDIUM.name -> BatteryMode.MEDIUM
                    BatteryMode.NORMAL.name -> BatteryMode.NORMAL
                    else -> throw IllegalArgumentException()
                }
            }.first()
            BatteryDetails(
                batteryCharge = percents,
                batteryMode = mode,
                isOptimized = isOptimized
            )
        }
            .catch { e ->
                e.printStackTrace()
            }
            .flowOn(dispatcher)

}