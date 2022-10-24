package ar.cleaner.first.pf.domain.usecases.battery

import ar.cleaner.first.pf.domain.exception.NonValidValuesException
import ar.cleaner.first.pf.domain.mapper.asRemainingTime
import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.models.details.BatteryDetails
import ar.cleaner.first.pf.domain.repositorys.battery.BatteryUseCaseRepository
import ar.cleaner.first.pf.domain.usecases.base.DefaultUseCase
import ar.cleaner.first.pf.domain.utils.isTimePassed
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetBatteryDetailsUseCase @Inject constructor(
    private val batteryUseCaseRepository: BatteryUseCaseRepository,
    private val dispatcher: CoroutineDispatcher
) : DefaultUseCase<BatteryDetails, Exception> {

    override fun invoke(): Flow<CaseResult<BatteryDetails, Exception>> =
        batteryUseCaseRepository.getBatteryPercents().combine(
            batteryUseCaseRepository.getRemainingTime()
        ) { percents, time ->
            val isOptimized = !isTimePassed(batteryUseCaseRepository.lastOptimizationMillis.first())
            val mode = batteryUseCaseRepository.getBatteryMode().map { mode ->
                when (mode) {
                    BatteryMode.HIGH.name -> BatteryMode.HIGH
                    BatteryMode.MEDIUM.name -> BatteryMode.MEDIUM
                    BatteryMode.NORMAL.name -> BatteryMode.NORMAL
                    else -> throw IllegalArgumentException()
                }
            }.first()
            val modeMultiplier = when (mode) {
                BatteryMode.HIGH -> 1.5
                BatteryMode.MEDIUM -> 1.3
                BatteryMode.NORMAL -> 1.1
            }
            BatteryDetails(
                batteryCharge = percents,
                batteryRemainingTime = (time * modeMultiplier).asRemainingTime(),
                batteryMode = mode,
                isOptimized = isOptimized
            )
        }.map {
            if (it.batteryCharge == 0 || it.batteryRemainingTime.isBlank())
                CaseResult.Failure(NonValidValuesException())
            else CaseResult.Success(it)
        }.catch { exception ->
            exception.printStackTrace()
        }.cancellable().flowOn(dispatcher)

}