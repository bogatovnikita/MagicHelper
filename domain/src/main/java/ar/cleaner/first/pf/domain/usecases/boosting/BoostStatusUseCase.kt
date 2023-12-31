package ar.cleaner.first.pf.domain.usecases.boosting

import ar.cleaner.first.pf.domain.gateways.boosting.BoostStatusRepository
import javax.inject.Inject

class BoostStatusUseCase @Inject constructor(
    private val boostStatusRepository: BoostStatusRepository
) {

    fun saveOptimizationStatus() = boostStatusRepository.saveOptimizationStatus()

    fun getOptimizationStatus(): Boolean = boostStatusRepository.getOptimizationStatus()

    fun getLastOptimizeRam(): Long = boostStatusRepository.getLastOptimizeRam()

    fun saveLastOptimizeRam(value: Long) = boostStatusRepository.saveLastOptimizeRam(value)
}