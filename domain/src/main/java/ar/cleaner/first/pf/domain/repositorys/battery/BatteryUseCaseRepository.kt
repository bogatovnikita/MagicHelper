package ar.cleaner.first.pf.domain.repositorys.battery

import ar.cleaner.first.pf.domain.repositorys.base.BaseRepository
import ar.cleaner.first.pf.domain.models.BatteryMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface BatteryUseCaseRepository : BaseRepository {
    fun getBatteryPercents(): StateFlow<Int>
    fun startOptimization(mode: BatteryMode): Flow<Int>
    fun getRemainingTime(): Flow<Double>
    fun getBatteryMode(): Flow<String>
    fun emulateOptimization(mode: BatteryMode): Flow<Int>
}