package ar.cleaner.first.pf.data.optimizers.base

import kotlinx.coroutines.flow.StateFlow

abstract class BaseOptimizer {
    abstract val lastOptimizationWorkMillis: StateFlow<Long>

    abstract fun setLastOptimizationTime(time: Long)
}