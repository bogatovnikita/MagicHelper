package ar.cleaner.first.pf.domain.gateways.boosting

import ar.cleaner.first.pf.domain.models.BackgroundApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface BoostingUseCaseRepository {

    fun getAvailableRam(scope: CoroutineScope? = null): StateFlow<Double>

    fun getTotalRam(): Double

    suspend fun getBackgroundApps(): List<BackgroundApp>

    fun startOptimization(backgroundApps: List<BackgroundApp>): Flow<Int>

    fun fastOptimization(): Flow<Int>
}