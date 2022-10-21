package ar.cleaner.first.pf.domain.repositorys.cooling

import ar.cleaner.first.pf.domain.models.App
import ar.cleaner.first.pf.domain.repositorys.base.BaseRepository
import kotlinx.coroutines.flow.Flow

interface CoolingUseCaseRepository : BaseRepository {
    fun startOptimization(): Flow<Int>

    fun getCpuTemperature(isTimePassed: Boolean): Double

    fun getRunningApps(): Flow<List<App>>
}