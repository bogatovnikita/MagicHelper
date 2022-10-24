package ar.cleaner.first.pf.data.optimizers.base

import kotlinx.coroutines.flow.Flow

interface OptimizationExecutor {
    fun runOptimization(): Flow<Int>
    fun emulateOptimization(): Flow<Int>
}