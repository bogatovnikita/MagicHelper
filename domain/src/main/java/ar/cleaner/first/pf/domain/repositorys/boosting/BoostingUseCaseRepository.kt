package ar.cleaner.first.pf.domain.repositorys.boosting

import ar.cleaner.first.pf.domain.models.BackgroundApp
import ar.cleaner.first.pf.domain.repositorys.base.BaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface BoostingUseCaseRepository : BaseRepository {

    suspend fun setHasOptimizeAll(hasOptimizeAll: Boolean)

    fun hasOptimizeAllFlow(): Flow<Boolean>

    fun getAvailableRam(scope: CoroutineScope? = null): StateFlow<Double>

    fun getTotalRam(): Double

    suspend fun getBackgroundApps(): List<BackgroundApp>

    fun startOptimization(backgroundApps: List<BackgroundApp>): Flow<Int>

    fun fastOptimization(): Flow<Int>
}